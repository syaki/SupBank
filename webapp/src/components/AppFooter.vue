<template>
  <div class="footer">
    <footer role="footer">
      <div class="footer-copyright">
        ©
        <span itemprop="copyrightYear">2019</span>
        <i class="dividerCol"></i>
        <span class="siteName">SupBank</span>
        <img class="icon" src="../assets/icon-location-64x64.png" alt="location icon">
        <span class="location">Tianjin - China</span>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'AppFooter',
};
</script>

<style scoped>
.footer {
  text-align: center;
  margin-top: 42px;
  margin-bottom: 24px;
}

.footer-copyright {
  font-size: 12px;
}

.footer-copyright .dividerCol {
  height: 8px;
  background: #444444;
}

.footer-copyright .icon {
  opacity: 1;
  height: 10px;
  width: 10px;
  vertical-align: -1px;
  margin-right: 6px;
  margin-left: 16px;
}
</style>
