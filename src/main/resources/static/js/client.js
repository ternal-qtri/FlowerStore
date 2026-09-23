/**
 * CLIENT JAVASCRIPT
 * Xử lý các tác vụ UI cơ bản: Responsive drawer, Slider, Sticky header, Filter tabs.
 * Tuyệt đối không chứa logic nghiệp vụ backend.
 */

document.addEventListener('DOMContentLoaded', function () {
  // 1. Sticky Header Effect
  const header = document.querySelector('.main-header');
  if (header) {
    window.addEventListener('scroll', function () {
      if (window.scrollY > 40) {
        header.classList.add('scrolled');
      } else {
        header.classList.remove('scrolled');
      }
    });
  }

  // 2. Mobile Menu Drawer
  const hamburgerBtn = document.querySelector('.hamburger-btn');
  const drawer = document.querySelector('.mobile-drawer');
  const drawerOverlay = document.querySelector('.mobile-drawer-overlay');
  const drawerClose = document.querySelector('.drawer-close');

  function openDrawer() {
    if (drawer && drawerOverlay) {
      drawer.classList.add('active');
      drawerOverlay.classList.add('active');
      document.body.style.overflow = 'hidden';
    }
  }

  function closeDrawer() {
    if (drawer && drawerOverlay) {
      drawer.classList.remove('active');
      drawerOverlay.classList.remove('active');
      document.body.style.overflow = '';
    }
  }

  if (hamburgerBtn) hamburgerBtn.addEventListener('click', openDrawer);
  if (drawerClose) drawerClose.addEventListener('click', closeDrawer);
  if (drawerOverlay) drawerOverlay.addEventListener('click', closeDrawer);

  // 3. Hero Carousel Slider
  const slides = document.querySelectorAll('.hero-slide');
  const dots = document.querySelectorAll('.slider-dots .dot');
  let currentSlide = 0;
  let slideInterval;

  function showSlide(index) {
    if (!slides.length) return;
    slides.forEach((slide, i) => {
      slide.classList.toggle('active', i === index);
    });
    dots.forEach((dot, i) => {
      dot.classList.toggle('active', i === index);
    });
    currentSlide = index;
  }

  function nextSlide() {
    let next = (currentSlide + 1) % slides.length;
    showSlide(next);
  }

  function startSlideShow() {
    if (slides.length > 1) {
      slideInterval = setInterval(nextSlide, 4500);
    }
  }

  function stopSlideShow() {
    clearInterval(slideInterval);
  }

  dots.forEach((dot, idx) => {
    dot.addEventListener('click', function () {
      stopSlideShow();
      showSlide(idx);
      startSlideShow();
    });
  });

  const heroSection = document.querySelector('.hero-section');
  if (heroSection) {
    heroSection.addEventListener('mouseenter', stopSlideShow);
    heroSection.addEventListener('mouseleave', startSlideShow);
    startSlideShow();
  }

  // 4. Product Category Filter Tabs (UI Only)
  const filterTabs = document.querySelectorAll('.filter-tab');
  const productCards = document.querySelectorAll('.product-card');

  filterTabs.forEach(tab => {
    tab.addEventListener('click', function () {
      filterTabs.forEach(t => t.classList.remove('active'));
      this.classList.add('active');

      const targetCategory = this.getAttribute('data-filter');
      productCards.forEach(card => {
        if (!targetCategory || targetCategory === 'all') {
          card.style.display = 'flex';
        } else {
          const cardCategory = card.getAttribute('data-category');
          if (cardCategory && cardCategory.includes(targetCategory)) {
            card.style.display = 'flex';
          } else {
            card.style.display = 'none';
          }
        }
      });
    });
  });
});
