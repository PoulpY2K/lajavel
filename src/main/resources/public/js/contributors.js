window.addEventListener("load", function () {
    console.log(document.querySelector("#showMenu"));
    document
        .querySelector("#showMenu")
        .addEventListener("click", function (event) {
            document.querySelector("#mobileNav").classList.remove("hidden");
        });

    document
        .querySelector("#hideMenu")
        .addEventListener("click", function (event) {
            document.querySelector("#mobileNav").classList.add("hidden");
        });
});
