const btn_btn_primary = document.getElementById("send-message"); //id不要加dot .
let token = window.localStorage.getItem("access_token");

let userId = window.localStorage.getItem("user_id");
let categoryId = 3;
let addPost_title = document.getElementById("addPost_title");
let addPost_content = document.getElementById("addPost_content");
let addPost_category = document.getElementById("addPost_category");
let log_out = document.getElementById("log_out"); //登出
let personal_blog = document.getElementById("personal_blog"); //父層
let card_html = document.getElementById("card-html");
let addPost_url = `http://localhost:8080/post/user/${userId}/category/${categoryId}/post`;
let search_post = document.getElementById("search_post");
// let findLastPost_url = `http://localhost:8080/post/last/${addPost_userId}`;
let PostByUserIdAndDesc_url = `http://localhost:8080/post/user/${userId}/posts/desc`;
log_out.addEventListener("click", () => {
  localStorage.removeItem("access_token");
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
  findPostByUserIdAndDesc();
  checkTokenIsExpired();
};

function checkTokenIsExpired() {
  let token = localStorage.getItem("access_token");
  if (token == null) {
    window.location.href = "frontpage.html";
  }
}
function findPostByUserIdAndDesc() {
  fetch(PostByUserIdAndDesc_url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + token,
    },

    body: JSON.stringify(userId),
  })
    .then(function (response) {
      return response.json();
    })
    .then((data) => {
      console.log(data);

      for (let i = 0; i < data.length; i++) {
        let title = document.createElement("h1");
        let data_title = data[i].title;
        let data_content = data[i].content;
        let data_author = data[i].author;
        title.innerHTML = "";
        card_html.innerHTML = "";
        let str = `<div class="card" style="width: 30rem;  ">
        <img src="./img/blog-img/2.jpg" class="card-img-top" alt="${data_title}">
        <div class="card-body">
        <h1>${data_title}</h1>
          <p class="card-text">
          ${data_content}</p>
          <h6 align=right>By ${data_author} </h6>
        </div>
      </div>`;

        title.innerHTML = str;

        document.getElementById("personal_blog").appendChild(title);

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
      card_html.innerHTML = "";

      if (data == "") {
        let alert = document.createElement("h1");
        alert.textContent = "無相關文章!!!";
        document.getElementById("personal_blog").appendChild(alert);
      }

      for (let i = 0; i < data.length; i++) {
        let title = document.createElement("h1");
        let data_title = data[i].title;
        let data_content = data[i].content;
        let data_author = data[i].author;
        title.innerHTML = "";
        card_html.innerHTML = "";
        let str = `<div class="card" style="width: 30rem;  ">
        <img src="./img/blog-img/2.jpg" class="card-img-top" alt="${data_title}">
        <div class="card-body">
        <h1>${data_title}</h1>
          <p class="card-text">
          ${data_content}</p>
          <h6 align=right>By ${data_author} </h6>
        </div>
      </div>`;

        title.innerHTML = str;

        document.getElementById("personal_blog").appendChild(title);

        //category.textContent = data_category;
      }
    })
    .catch((error) => {
      console.log(error);
      console.log("fail2");
    });
});
