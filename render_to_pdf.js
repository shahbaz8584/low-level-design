const fs = require('fs');
const path = require('path');
const puppeteer = require('puppeteer');

(async () => {
  try {
    const htmlPath = path.resolve('All_Design_Patterns.html');
    let html = fs.readFileSync(htmlPath, 'utf8');

    // Convert code blocks with language-mermaid into <div class="mermaid">...</div>
    html = html.replace(/<pre><code class="language-mermaid">([\s\S]*?)<\/code><\/pre>/g, (m, p1) => {
      const decoded = p1
        .replace(/&lt;/g, '<')
        .replace(/&gt;/g, '>')
        .replace(/&amp;/g, '&');
      return `<div class="mermaid">${decoded}</div>`;
    });

    // Inject Mermaid JS before </body>
    const mermaidScript = `\n<script src="https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"></script>\n<script>mermaid.initialize({startOnLoad:true, theme: 'default'});</script>\n`;
    if (html.includes('</body>')) {
      html = html.replace('</body>', mermaidScript + '</body>');
    } else {
      html += mermaidScript;
    }

    const browser = await puppeteer.launch({args: ['--no-sandbox', '--disable-setuid-sandbox']});
    const page = await browser.newPage();
    await page.setContent(html, {waitUntil: 'networkidle0'});

    // Give Mermaid some time to render diagrams
    await new Promise(res => setTimeout(res, 1500));

    const outPdf = path.resolve('All_Design_Patterns.pdf');
    await page.pdf({path: outPdf, format: 'A4', printBackground: true});

    await browser.close();
    console.log('PDF generated at', outPdf);
  } catch (err) {
    console.error('Error:', err);
    process.exit(1);
  }
})();
