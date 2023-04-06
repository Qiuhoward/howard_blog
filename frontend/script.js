const logBox = document.querySelector(".log-box");
const registerLink = document.querySelector(".register-link");
const loginLink = document.querySelector(".login-link");


registerLink.addEventListener("click", () => {
  logBox.classList.add("active");
}); 

loginLink.addEventListener("click", () => {
  logBox.classList.remove("active");
});
