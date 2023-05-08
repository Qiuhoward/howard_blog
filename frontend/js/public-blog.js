const btn_btn_primary = document.getElementById("send-message"); //id不要加dot .
const token = window.localStorage.getItem("access_token");

let userId = window.localStorage.getItem("user_id");
let categoryId = 3;
let personal_blog = document.getElementById("personal_blog"); //父層
let addPost_url = `http://localhost:8080/post/user/${userId}/category/${categoryId}/post`;
let search_post = document.getElementById("search_post");
let postId;
let card_html = document.getElementById("card-html");
let PostByDesc_url = `http://localhost:8080/post/?pageSize=15&pageNumber=0&sortBy=postId&sortDir=desc`;
let editPost_url = "http://localhost:8080/post/6?content=git&title=git";//編輯
let button_edit = document.getElementById("button_edit");
let button_delete = document.getElementById("button_delete");
//修改文章
// .addEventListener('click',()=>{

//   點下去innerHeight.html改成這個並且抓職發到後端 ，後端資料庫更改資料在回傳postDTO
//     <form class="form">
//     <h2>CONTACT US</h2>
//     <p type="文章標題:" id=edit-title><input placeholder=""></input>
//   </p>
//     <p type="類別:" id=edit-type><input placeholder=""></input></p>
//     <
//   </form>p type="文章內容:" id=edit-content><input placeholder=""></input></p>
//     <button>提交</button>
// })

//刪除文章


//新增文章
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
      let author = data.author;

      addPost_title.innerHTML = title;
      addPost_content.textContent = content + author;
      // addPost_category.textContent = category;
      console.log(title, content, type);

      alert("文章新增成功");
      // window.location.href = index_url;

      window.location.href = "http://localhost:5500/public-blog.html";
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
  findPostByDesc();
  checkTokenIsExpired();
};
function checkTokenIsExpired() {

  let token = localStorage.getItem("access_token");
  if (token == null) {
    window.location.href = "frontpage.html";
  }
}
function findPostByDesc() {
  fetch(PostByDesc_url, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + token,
      pageSize: "15",
      pageNumber: 0,
      sortBy: postId,
      sortDir: "desc",
    },
  })
    .then(function (response) {
      return response.json();
    })
    .then((data) => {
      console.log(data);
      let contentList = data.content;
      for (let i = 0; i < contentList.length; i++) {
        let title = document.createElement("h1");
        let data_title = contentList[i].title;
        let data_content = contentList[i].content;
        let data_author = contentList[i].author;
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

        document.getElementById("public_blog").appendChild(title);

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
    },
  })
    .then(function (response) {
      return response.json();
    })
    .then((data) => {
      console.log(data);
      public_blog.innerHTML = "";
      //clean result
      if (data == "") {
        let alert = document.createElement("h1");
        alert.textContent = "無相關文章!!";
        document.getElementById("public_blog").appendChild(alert);
        return false;
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

        document.getElementById("public_blog").appendChild(title);

        //category.textContent = data_category;
      }
    })
    .catch((error) => {
      console.log(error);
      console.log("fail2");
    });
});


