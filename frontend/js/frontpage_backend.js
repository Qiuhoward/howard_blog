const logBox = document.querySelector(".log-box");
const loginLink = document.querySelector(".login-link");
const btn_login = document.querySelector(".btn_login");
let rule_userName = /^[a-z0-9]+@[a-z]+.com$/i; //^開始 $結束

//是否登入過
// if (localStorage.getItem("token")) {
//   window.location.href = "http://localhost:5500/index.html";
// }

btn_login.addEventListener("click", (e) => {
  e.preventDefault();
  let userName = document.getElementById("1").value;

  if (!rule_userName.test(userName)) {
    alert("帳號格式錯誤");
    return false;
  }

  let password = document.getElementById("2").value;

  fetch("http://localhost:8080/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ userName, password }),
  })
    .then(function (response) {
      return response.json();
    })
    .then((data) => {
      window.localStorage.setItem("access_token", data.accessToken);
      window.localStorage.setItem("user_id", data.userDto.userId);
      console.log(data.accessToken);
      if (data.accessToken != null) {
        alert("login success");
        window.location.href = "http://localhost:5500/index_backend.html";
      }
      //如果對那就把資料存起來並導到首頁
    })
    .catch((error) => {
      console.log("fail2");
    });
  return false;
});
