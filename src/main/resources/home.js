
const wrapper = document.getElementById("loginWrapper");
const loginBox = document.getElementById("loginBox");
const registerBox = document.getElementById("registerBox");
const btnGoRegister = document.getElementById("btnGoRegister");
const btnGoLogin = document.getElementById("btnGoLogin");
const btnLogin = document.getElementById("btnLogin");
const loginEmail = document.getElementById("loginEmail");
const loginSenha = document.getElementById("loginSenha");


btnGoRegister.addEventListener("click", () => {
    wrapper.classList.add("register-mode");
    loginBox.classList.remove("active");
    registerBox.classList.add("active");
});

btnGoLogin.addEventListener("click", () => {
    wrapper.classList.remove("register-mode");
    registerBox.classList.remove("active");
    loginBox.classList.add("active");
});

// Carrossel
const track = document.getElementById("carousel-track");
const items = Array.from(track.children);
const btnPrev = document.getElementById("btnPrev");
const btnNext = document.getElementById("btnNext");

const gap = 30;
const itemsVisible = 3;
const itemWidth = items[0].offsetWidth + gap;

for (let i = items.length - itemsVisible; i < items.length; i++) {
    const clone = items[i].cloneNode(true);
    clone.classList.add("clone");
    track.insertBefore(clone, track.firstChild);
}

for (let i = 0; i < itemsVisible; i++) {
    const clone = items[i].cloneNode(true);
    clone.classList.add("clone");
    track.appendChild(clone);
}

const allItems = Array.from(track.children);
let index = itemsVisible;  

track.style.transform = `translateX(${-itemWidth * index}px)`;

function moveCarousel() {
    track.style.transition = "transform 0.45s ease";
    track.style.transform = `translateX(${-itemWidth * index}px)`;
}

btnNext.addEventListener("click", () => {
    index++;
    moveCarousel();
});

btnPrev.addEventListener("click", () => {
    index--;
    moveCarousel();
});

track.addEventListener("transitionend", () => {
    if (allItems[index].classList.contains("clone")) {
        track.style.transition = "none";
        if (index >= allItems.length - itemsVisible) {
            index = itemsVisible;
        } else if (index < itemsVisible) {
            index = allItems.length - (itemsVisible * 2);
        }
        track.style.transform = `translateX(${-itemWidth * index}px)`;
    }
});


// Menu de hamburguer para mobile

document.addEventListener('DOMContentLoaded', () => {
  const hamburgerBtn = document.getElementById('hamburgerBtn');
  const mobileMenu = document.getElementById('mobileMenu');
  const navMenu = document.querySelector('.nav-menu');
  let overlay;

  if (navMenu && mobileMenu) {
    mobileMenu.innerHTML = '';
    const links = navMenu.querySelectorAll('a');
    links.forEach(a => {
      const clone = a.cloneNode(true);
      clone.addEventListener('click', () => closeMenu());
      mobileMenu.appendChild(clone);
    });
  }

  overlay = document.createElement('div');
  overlay.className = 'body-dim';
  document.body.appendChild(overlay);
  overlay.addEventListener('click', () => closeMenu());

  function openMenu(){
    hamburgerBtn.classList.add('active');
    mobileMenu.classList.add('open');
    overlay.classList.add('show');
    hamburgerBtn.setAttribute('aria-expanded', 'true');
    mobileMenu.setAttribute('aria-hidden', 'false');
    // lock scroll
    document.documentElement.style.overflow = 'hidden';
    document.body.style.overflow = 'hidden';
  }

  function closeMenu(){
    hamburgerBtn.classList.remove('active');
    mobileMenu.classList.remove('open');
    overlay.classList.remove('show');
    hamburgerBtn.setAttribute('aria-expanded', 'false');
    mobileMenu.setAttribute('aria-hidden', 'true');
    document.documentElement.style.overflow = '';
    document.body.style.overflow = '';
  }

  hamburgerBtn.addEventListener('click', () => {
    if (mobileMenu.classList.contains('open')) closeMenu();
    else openMenu();
  });

  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && mobileMenu.classList.contains('open')) {
      closeMenu();
    }
  });

  window.addEventListener('resize', () => {
    if (window.innerWidth > 820) {
      closeMenu();
    }
  });
});
