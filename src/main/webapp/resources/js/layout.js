document.addEventListener("DOMContentLoaded", function () {
  const toggleBtn = document.getElementById("toggleSidebar");
  const sidebar = document.querySelector(".side-navbar");
  toggleBtn.addEventListener("click", function () {
    sidebar.classList.toggle("shrinked");
  });
});