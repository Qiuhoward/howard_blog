const btn_btn_primary = document.getElementById("send-message"); //id不要加dot .
const token = window.localStorage.getItem("token");

let userId = 3;
let categoryId = 3;
let addPost_title = document.getElementById("addPost_title");
let addPost_content = document.getElementById("addPost_content");
let addPost_category = document.getElementById("addPost_category");
let log_out = document.getElementById("log_out"); //登出
let personal_blog = document.getElementById("personal_blog"); //父層
let addPost_userId = 1;
let addPost_url = `http://localhost:8080/post/user/${userId}/category/${categoryId}/post`;
let search_post = document.getElementById("search_post");
// let findLastPost_url = `http://localhost:8080/post/last/${addPost_userId}`;
let findPostByUserId_url = `http://localhost:8080/post/user/${userId}/posts/desc`;

log_out.addEventListener("click", () => {
  localStorage.removeItem("token");
});
btn_btn_primary.addEventListener("click", () => {
  let title = document.getElementById("title").value;
  let type = document.getElementById("type").value;
  let content = document.getElementById("content").value;
  fetch(addPost_url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + token,
    },
    body: JSON.stringify({ userId, categoryId, title, type, content }),
  })
    .then(function (response) {
      return response.json();
    })
    .then((data) => {
      let title = data.title;
      let content = data.content;
      let type = data.type;
      addPost_title.textContent = title;
      addPost_content.textContent = content;
      // addPost_category.textContent = category;
      console.log(title, content, type);

      alert("文章新增成功");
      // window.location.href = index_url;

      window.location.href = "http://localhost:5500/personal-blog.html";
    })
    .catch((error) => {
      console.log(error);
      console.log("fail2");
    });
});
//登入以後要記下他的userId，Token現在為登入就創立新的一次(改為登入就刷新)
//token有時候會有問題(登入過期就要再重新登入一次)使用者感官不好
//
//要存在哪裡 以及 作者改為分類 分類存到post資料庫(後端)
window.onload = function () {
  findPostByUserId();
  checkTokenIsExpired();
};
function checkTokenIsExpired() {

  let token = localStorage.getItem("token");
  if (token == null) {
    window.location.href = "frontpage.html";
  }
}
function findPostByUserId() {
  fetch(findPostByUserId_url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + token,
    },
    body: JSON.stringify({ userId }),
  })
    .then(function (response) {
      return response.json();
    })
    .then((data) => {
      console.log(data);

      for (let i = 0; i < data.length; i++) {
        let title = document.createElement("h1");
        let content = document.createElement("p");

        let data_title = data[i].title;
        let data_content = data[i].content;

        title.textContent = data_title;
        content.textContent = data_content;
        document.getElementById("personal_blog").appendChild(title);
        document.getElementById("personal_blog").appendChild(content);

        //category.textContent = data_category;
      }
      return false;
    })
    .catch((error) => {
      console.log(error);
      console.log("fail2");
    });
}
search_post.addEventListener("keyup", () => {
  let keyword = search_post.value;
  let findPostByKeyword_url = `http://localhost:8080/post/keyword?keyword=${keyword}`;
  fetch(findPostByKeyword_url, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + token,
      keyword: keyword,
    },
  })
    .then(function (response) {
      return response.json();
    })

    .then((data) => {
      console.log(data);
      //clean result
      personal_blog.innerHTML = "";
      if (data == "") {
        let alert = document.createElement("h1");
        alert.textContent = "無相關文章!!!";
        document.getElementById("personal_blog").appendChild(alert);
      }

      for (let i = 0; i < data.length; i++) {
        let title = document.createElement("h1");
        let content = document.createElement("p");

        let data_title = data[i].title;
        let data_content = data[i].content;

        title.textContent = data_title;
        content.textContent = data_content;
        document.getElementById("personal_blog").appendChild(title);
        document.getElementById("personal_blog").appendChild(content);

        //category.textContent = data_category;
      }
      return false;
    })
    .catch((error) => {
      console.log(error);
      console.log("fail2");
    });
});

// function getLastFunction() {
//   fetch(findLastPost_url, {
//     method: "POST",
//     headers: {
//       "Content-Type": "application/json",
//       Authorization: "Bearer " + token,
//     },
//     body: JSON.stringify({ userId }),
//   })
//     .then(function (response) {
//       return response.json();
//     })
//     .then((data) => {
//       let title = data.title;
//       addPost_title.textContent = title;

//       let content = data.content;
//       addPost_content.textContent = content;

//       let category = data.category;
//       // addPost_category.textContent = category;

//       console.log(title, content, category);
//       return false;
//       alert("文章新增成功");
//       // window.location.href = index_url;

//       window.location.href = "http://localhost:5500/index.html";
//     })
//     .catch((error) => {
//       console.log(error);
//       console.log("fail2");
//     });
// }
