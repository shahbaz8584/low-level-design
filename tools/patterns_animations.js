(function(){
  function escapeHtml(s){return (s||'').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');}
  function parseSections(md){
    const lines = md.split(/\r?\n/);
    const sections = {};
    let cur = 'intro'; sections[cur] = '';
    for(let i=0;i<lines.length;i++){
      const line = lines[i];
      const m = line.match(/^#{1,6}\s*(.+)/);
      if(m){ cur = m[1].trim().toLowerCase(); sections[cur] = sections[cur] || ''; }
      else { sections[cur] += line + '\n'; }
    }
    return sections;
  }
  function extractFirstImage(md){ const m = md.match(/!\[[^\]]*\]\(([^)]+)\)/); return m?m[1]:null; }
  function extractJavaCode(md){ const m = md.match(/```\s*java\n([\s\S]*?)```/i); if(m) return m[1].trim(); const m2 = md.match(/```\n([\s\S]*?)```/); return m2?m2[1].trim():null; }
  function simpleMdToHtml(md){ let html = escapeHtml(md||''); html = html.replace(/```\s*([a-zA-Z0-9-]*)\n([\s\S]*?)```/g,(m,lang,code)=>{return '<pre><code class="lang-'+(lang||'')+'">'+escapeHtml(code)+'</code></pre>';}); html = html.replace(/!\[(.*?)\]\((.*?)\)/g, '<img alt="$1" src="$2">'); html = html.replace(/\[([^\]]+)\]\(([^)]+)\)/g,'<a href="$2">$1</a>'); html = html.replace(/\*\*(.+?)\*\*/g,'<strong>$1</strong>'); html = html.replace(/\*(.+?)\*/g,'<em>$1</em>'); html = html.split(/\n\s*\n/).map(p=>'<p>'+p.replace(/\n/g,' ')+'</p>').join('\n'); return html; }
  function findSection(sections, candidates){ for(const c of candidates){ const key = Object.keys(sections).find(k=>k.includes(c)); if(key) return sections[key]; } return null; }
  function buildModal(title, md){
    const sections = parseSections(md);
    const what = findSection(sections,['what','intent','summary','description']) || sections['intro'] || '';
    const problem = findSection(sections,['problem','problem statement','problem-statement']) || '';
    const why = findSection(sections,['why','advantages','when not','why-simple','why use']) || '';
    const umlImg = extractFirstImage(md);
    const javaCode = extractJavaCode(md);
    const modalBg = document.createElement('div'); modalBg.className='pattern-modal-backdrop';
    const modal = document.createElement('div'); modal.className='pattern-modal';
    modal.innerHTML = '<div class="modal-header"><h2>'+escapeHtml(title)+'</h2><button class="close-btn" aria-label="Close">✕</button></div>';
    const body = document.createElement('div'); body.className='modal-body';
    const main = document.createElement('div'); main.className='main';
    main.innerHTML = '<h3>What is this pattern</h3>'+simpleMdToHtml(what)+'<h3>Problem it solves</h3>'+simpleMdToHtml(problem)+'<h3>Why we need it</h3>'+simpleMdToHtml(why);
    const aside = document.createElement('div'); aside.className='aside';
    if(umlImg){ aside.innerHTML = '<h4>UML / Diagram</h4><img src="'+umlImg+'">'; }
    else{ const umlSec = findSection(sections,['uml','class diagram','uml / class']); if(umlSec) aside.innerHTML = '<h4>UML / Diagram</h4>'+simpleMdToHtml(umlSec); else aside.innerHTML='<h4>UML / Diagram</h4><div class="muted">No UML found</div>'; }
    if(javaCode){ aside.innerHTML += '<h4>Java Example</h4><pre><code>'+escapeHtml(javaCode)+'</code></pre>'; }
    body.appendChild(main); body.appendChild(aside);
    modal.appendChild(body); modalBg.appendChild(modal); document.body.appendChild(modalBg);
    modalBg.addEventListener('click', (e)=>{ if(e.target===modalBg) close(); }); modal.querySelector('.close-btn').addEventListener('click', close);
    function escClose(e){ if(e.key==='Escape') close(); }
    document.addEventListener('keydown', escClose);
    function close(){ document.removeEventListener('keydown', escClose); modalBg.remove(); }
  }
  function openFromHref(href, title){
    try{
      if(typeof href === 'string' && href.trim().toLowerCase().endsWith('.html')){
        // navigate to generated HTML page for full readable view
        window.location.href = href;
        return;
      }
    }catch(e){}
    fetch(href).then(r=>{ if(!r.ok) throw new Error('fetch failed'); return r.text(); }).then(md=>buildModal(title, md)).catch(()=>{ window.location.href = href; }); }
  function slugifyTitle(s){ return (s||'').toLowerCase().replace(/[^a-z0-9]+/g,'-').replace(/^-+|-+$/g,''); }
  document.addEventListener('DOMContentLoaded', function(){ document.querySelectorAll('.card').forEach(card=>{ card.classList.add('reveal'); card.addEventListener('click', ()=>{ const href = card.getAttribute('data-href'); const title = (card.querySelector('h3') && card.querySelector('h3').innerText) || ''; const htmlPage = 'All_Design_Patterns_' + slugifyTitle(title) + '.html'; window.location.href = htmlPage; }); card.addEventListener('keypress', (e)=>{ if(e.key==='Enter'){ card.click(); }}); }); });
})();
