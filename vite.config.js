import { defineConfig } from 'vite';
import { resolve } from 'path';

export default defineConfig({
  build: {
    outDir: resolve(__dirname, 'src/main/resources/static'),
    emptyOutDir: true,
    cssCodeSplit: false,
    rollupOptions: {
      input: resolve(__dirname, 'src/main/frontend/main.js'),
      output: {
        entryFileNames: 'assets/main.js',      // ← [hash] 제거
        assetFileNames: 'assets/main.css',     // ← [hash] 제거
      },
    },
  },
});
