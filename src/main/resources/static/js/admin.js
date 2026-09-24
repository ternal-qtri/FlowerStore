/**
 * ADMIN JAVASCRIPT - VƯỜN HOA TƯƠI ADMIN DASHBOARD
 * Handles sidebar toggles, table filters, tab navigation, quick modals, and file uploads.
 */

document.addEventListener('DOMContentLoaded', function () {
  // 1. Sidebar Mobile Toggle
  const toggleBtn = document.querySelector('.toggle-sidebar-btn');
  const sidebar = document.querySelector('.admin-sidebar');
  if (toggleBtn && sidebar) {
    toggleBtn.addEventListener('click', function () {
      sidebar.classList.toggle('active');
    });
  }

  // 2. Tab Filter Navigation
  const tabFilterBtns = document.querySelectorAll('.tab-filter-btn');
  tabFilterBtns.forEach(btn => {
    btn.addEventListener('click', function () {
      const parent = this.closest('.tab-filter-bar');
      if (parent) {
        parent.querySelectorAll('.tab-filter-btn').forEach(b => b.classList.remove('active'));
      }
      this.classList.add('active');

      const statusFilter = this.getAttribute('data-status');
      const targetTable = document.querySelector('.filterable-table');
      if (targetTable && statusFilter) {
        const rows = targetTable.querySelectorAll('tbody tr');
        rows.forEach(row => {
          const rowStatus = row.getAttribute('data-status');
          if (statusFilter === 'all' || rowStatus === statusFilter) {
            row.style.display = '';
          } else {
            row.style.display = 'none';
          }
        });
      }
    });
  });

  // 4. Image Upload File Preview
  const imageInputs = document.querySelectorAll('.image-upload-input');
  imageInputs.forEach(input => {
    input.addEventListener('change', function (e) {
      const file = e.target.files[0];
      const previewId = this.getAttribute('data-preview-target');
      const previewImg = document.getElementById(previewId);
      if (file && previewImg) {
        const reader = new FileReader();
        reader.onload = function (evt) {
          previewImg.src = evt.target.result;
          previewImg.style.display = 'block';
        };
        reader.readAsDataURL(file);
      }
    });
  });
});
