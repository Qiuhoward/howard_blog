const logBox = document.querySelector(".log-box");
const registerLink = document.querySelector(".register-link");
const loginLink = document.querySelector(".login-link");

const btn_login = document.querySelector(".btn_login");
const btn_register = document.querySelector(".btn_register");
let rule_userName = /^[a-z0-9]+@[a-z]+.com$/i; //^開始 $結束

registerLink.addEventListener("click", () => {
  logBox.classList.add("active");
});

loginLink.addEventListener("click", () => {
  logBox.classList.remove("active");
});
console.log(localStorage.getItem("token"));
//是否登入過
if (localStorage.getItem("token")) {
  window.location.href = "http://localhost:5500/index.html";
}

btn_register.addEventListener("click", () => {
  let userName = document.getElementById("3").value;
  console.log(rule_userName.test(userName));
  //rule_email.test(email) ?  : alert("請依信箱格式填寫")//下面警告說不符合規則

  //送出如果不符合格式的話自動重新導向原頁並提示哪裡有錯
  let password = document.getElementById("4").value;
  let name = document.getElementById("5").value;
  let mobile = document.getElementById("6").value;

  // Send data to API
  fetch("http://localhost:8080/auth/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ userName, password, name, mobile }),
  })
    .then(function (response) {
      return response.json;
    })
    .then((data) => {
      localStorage.setItem("token", data.token);
      console.log(data.token);
      if (data.token != null) {
        window.location.href = "http://localhost:5500/index.html";
      }
    })
    .catch((error) => {
      console.log("fail");
    });
  return false;
});

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
      window.localStorage.removeItem("token");
      window.localStorage.setItem("token", data.token);
      console.log(data.token);
      if (data.token != null) {
        alert("login success");
        window.location.href = "http://localhost:5500/index.html";
      }
      //如果對那就把資料存起來並導到首頁
    })
    .catch((error) => {
      console.log("fail2");
    });
  return false;
});
