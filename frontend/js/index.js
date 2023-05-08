

let log_out=document.getElementById("log_out");//登出

log_out.addEventListener('click',()=>{
    localStorage.removeItem("access_token");
  })