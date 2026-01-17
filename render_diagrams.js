const fs = require('fs');
const path = require('path');
const puppeteer = require('puppeteer');

(async ()=>{
  const base = path.resolve('LLD/DesignPattern');
  const outDir = path.resolve('build/diagrams');
  fs.mkdirSync(outDir, {recursive:true});
  const files = [];

  function walk(dir){
    for(const name of fs.readdirSync(dir)){
      const p = path.join(dir,name);
      const stat = fs.statSync(p);
      if(stat.isDirectory()) walk(p);
      else if(name==='ClassDiagram.md' && p.includes(path.join('UML', 'ClassDiagram.md'))){
        files.push(p);
      }
    }
  }
  walk(base);
  console.log('Found', files.length, 'UML files');
  const browser = await puppeteer.launch({args:['--no-sandbox','--disable-setuid-sandbox']});
  const page = await browser.newPage();
  page.setDefaultNavigationTimeout(60000);
  for(const f of files){
    const rel = path.relative(base, f);
    const safeName = rel.replace(/[\\/]/g,'_').replace(/[^a-zA-Z0-9_\-\.]/g,'');
    const out = path.join(outDir, safeName + '.png');
    const content = fs.readFileSync(f,'utf8');
    const m = content.match(/```mermaid\n([\s\S]*?)\n```/m);
    if(!m){
      console.log('No mermaid block in', f); continue;
    }
    const diagram = m[1];
    const html = `<!doctype html><html><head><meta charset="utf-8"><style>body{margin:0;padding:0;} .mermaid{width:100%;height:100%;}</style></head><body><div class="mermaid">${diagram}</div><script src="https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"></script><script>mermaid.initialize({startOnLoad:true, securityLevel:'loose'});</script></body></html>`;
    try{
      await page.setContent(html,{waitUntil:'domcontentloaded', timeout:60000});
    }catch(e){
      console.error('setContent timeout for', f, e.message);
      continue;
    }
    // wait for mermaid to render
    await new Promise(r=>setTimeout(r,1200));
    const el = await page.$('.mermaid');
    if(!el){
      console.log('No .mermaid element for', f); continue;
    }
    await el.screenshot({path:out});
    console.log('Rendered', f, '->', out);
  }
  await browser.close();
})();
