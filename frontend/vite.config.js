import { defineConfig } from 'vite';
import { resolve } from 'path';

export default defineConfig({
  root: './',
  build: {
    outDir: '../backend/src/main/resources/static',
    emptyOutDir: true,
    rollupOptions: {
      input: {
        main: resolve(__dirname, 'index.html'),
        planList: resolve(__dirname, 'pages/plan/list.html'),
        planDetail: resolve(__dirname, 'pages/plan/detail.html'),
        subscribeForm: resolve(__dirname, 'pages/subscribe/form.html'),
        subscribeResult: resolve(__dirname, 'pages/subscribe/result.html'),
        mySubscriptions: resolve(__dirname, 'pages/member/subscriptions.html'),
        recommendation: resolve(__dirname, 'pages/recommendation.html'),
        cancellationLog: resolve(__dirname, 'pages/cancellation-log.html'),
      },
      output: {
        entryFileNames: `js/[name].js`,
        chunkFileNames: `js/[name].js`,
        assetFileNames: `css/[name].[ext]`
      }
    }
  },
  server: {
    port: 5173,
    open: true
  }
});
