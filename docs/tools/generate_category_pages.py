#!/usr/bin/env python3
"""
A safe replacement generator that creates category pages and per-pattern SVG thumbnails.
Writes: All_Design_Patterns_Behavioral.html, All_Design_Patterns_Creational.html, All_Design_Patterns_Structural.html
"""
import os
from pathlib import Path
import html
import re
import shutil
import json

ROOT = Path(__file__).resolve().parents[1]
PATTERN_ROOT = ROOT / 'LLD' / 'DesignPattern'
OUT = ROOT
PATTERN_IMG_DIR = OUT / 'tools' / 'pattern_images'

# When True, regenerate motif SVG thumbnails for patterns that don't have
# pattern-local images and overwrite existing SVG placeholders.
REGENERATE_MOTIFS = True

CATEGORY_MAP = {
    'behavioral': 'Behavioral',
    'behavior': 'Behavioral',
    'creational': 'Creational',
    'creationaldesign': 'Creational',
    'structural': 'Structural'
}

TEMPLATE_HEAD = '''<!doctype html>
<html><head><meta charset="utf-8"><title>{title}</title>
<link rel="stylesheet" href="tools/index_style.css">
<link rel="stylesheet" href="tools/patterns_responsive.css">
<script defer src="tools/patterns_animations.js"></script>
</head><body>
<div class="container">
    <aside class="sidebar">
        <div class="brand">Design Patterns</div>
        <div class="search"><input oninput="(e=>{const q=e.value.toLowerCase();document.querySelectorAll('.card').forEach(c=>{const t=(c.getAttribute('data-title')|| (c.querySelector('h3')&&c.querySelector('h3').innerText)||'').toLowerCase();c.style.display=t.includes(q)?'block':'none'})})(this)" placeholder="Search patterns..."></div>
        <nav>
              [[NAV]]
        </nav>
    </aside>
    <main class="main">
        <div class="header"><h1>{title}</h1></div>
        <article>
'''

TEMPLATE_FOOT = '''
</article>
</main>
</div>
</body></html>'''


def slugify(name: str):
    return ''.join(c if c.isalnum() else '-' for c in name).strip('-').lower()


def strip_interview_suffix(text: str):
    if not text:
        return ''
    return re.sub(r"\s*[—-]\s*Interview Reference\b", '', text, flags=re.IGNORECASE).strip()


def read_summary(folder: Path):
    # first paragraph from PatternReference.md or README.md
    for candidate in ('PatternReference.md', 'README.md'):
        p = folder / candidate
        if p.exists():
            txt = p.read_text(encoding='utf-8')
            # remove headings
            txt = re.sub(r"^#+\s*", '', txt, flags=re.MULTILINE)
            parts = [p.strip() for p in re.split(r"\n\s*\n", txt) if p.strip()]
            if parts:
                return html.escape(parts[0].splitlines()[0])
    return ''


def ensure_svg_for_pattern(title: str, category: str, dest: Path):
    try:
        text = html.escape(title or '')
        w, h = 400, 240
        lowt = title.lower() if title else ''
        # choose a motif based on pattern name keywords
        # stronger strokes and colors for better contrast
        if 'factory' in lowt:
              motif = """<g><rect x='40' y='40' width='120' height='80' rx='8' fill='#fff' stroke='#1f2937' stroke-width='3'/><rect x='180' y='56' width='120' height='48' rx='8' fill='#fff' stroke='#f97316' stroke-width='3'/></g>"""
        elif 'adapter' in lowt:
              motif = """<g><rect x='40' y='56' width='80' height='56' rx='8' fill='#fff' stroke='#1f2937' stroke-width='3'/><rect x='140' y='56' width='120' height='56' rx='8' fill='#fff' stroke='#7c3aed' stroke-width='3'/></g>"""
        elif 'bridge' in lowt:
              motif = """<g><rect x='40' y='56' width='120' height='56' rx='8' fill='#fff' stroke='#1f2937' stroke-width='3'/><line x1='160' y1='84' x2='240' y2='84' stroke='#1f2937' stroke-width='6' stroke-linecap='round'/></g>"""
        elif 'flyweight' in lowt:
              motif = """<g><circle cx='100' cy='84' r='28' fill='#fff' stroke='#1f2937' stroke-width='3'/><circle cx='160' cy='84' r='16' fill='#fff' stroke='#1f2937' stroke-width='2'/></g>"""
        elif 'decorator' in lowt:
              motif = """<g><rect x='36' y='48' width='220' height='88' rx='8' fill='#fff' stroke='#1f2937' stroke-width='2.5'/><rect x='56' y='64' width='40' height='56' rx='6' fill='#fff' stroke='#1f2937' stroke-width='1.8'/></g>"""
        elif 'composite' in lowt:
              motif = """<g><rect x='60' y='60' width='60' height='40' rx='6' fill='#fff' stroke='#1f2937' stroke-width='2.5'/><rect x='140' y='60' width='90' height='40' rx='6' fill='#fff' stroke='#1f2937' stroke-width='2.5'/></g>"""
        elif 'proxy' in lowt:
              motif = """<g><rect x='60' y='60' width='120' height='60' rx='8' fill='#fff' stroke='#1f2937' stroke-width='2.5'/><rect x='200' y='60' width='60' height='60' rx='8' fill='#fff' stroke='#1f2937' stroke-width='1.8' opacity='0.9'/></g>"""
        elif 'observer' in lowt:
              motif = """<g><ellipse cx='110' cy='88' rx='44' ry='28' fill='#fff' stroke='#1f2937' stroke-width='2.5'/><circle cx='230' cy='88' r='8' fill='#fff' stroke='#1f2937' stroke-width='2'/></g>"""
        elif any(k in lowt for k in ('strategy','state','template')):
            motif = """<g><rect x='48' y='60' width='48' height='56' rx='6' fill='#fff' stroke='#dbeafe'/><rect x='112' y='60' width='48' height='56' rx='6' fill='#fff' stroke='#e6eefc'/></g>"""
        elif 'chain' in lowt or 'responsibil' in lowt:
            motif = """<g><rect x='40' y='76' width='48' height='28' rx='6' fill='#fff' stroke='#93c5fd'/><rect x='96' y='76' width='48' height='28' rx='6' fill='#fff' stroke='#a78bfa'/></g>"""
        elif 'memento' in lowt:
            motif = """<g><rect x='56' y='56' width='80' height='64' rx='6' fill='#fff6f8' stroke='#fb7185' stroke-width='2'/><rect x='156' y='76' width='36' height='28' rx='6' fill='#fff' stroke='#e11d48' stroke-width='1.5'/></g>"""
        elif 'object' in lowt and 'pool' in lowt:
            motif = """<g><rect x='40' y='64' width='48' height='44' rx='6' fill='#fff' stroke='#dbeafe'/><rect x='100' y='64' width='48' height='44' rx='6' fill='#fff' stroke='#e6eefc'/></g>"""
        elif 'prototype' in lowt:
            motif = """<g><rect x='60' y='64' width='60' height='48' rx='6' fill='#fff' stroke='#1e40af' stroke-width='2'/><rect x='136' y='64' width='36' height='36' rx='6' fill='#fff' stroke='#2563eb' stroke-width='1.5' opacity='0.95'/></g>"""
        elif 'builder' in lowt:
            motif = """<g><rect x='48' y='56' width='40' height='56' rx='6' fill='#fff' stroke='#c7d2fe'/><rect x='104' y='56' width='40' height='56' rx='6' fill='#fff' stroke='#e6eefc'/></g>"""
        elif 'singleton' in lowt:
            motif = """<g><circle cx='180' cy='88' r='40' fill='#fff' stroke='#7c3aed' stroke-width='2'/><circle cx='180' cy='88' r='12' fill='#eef2ff' stroke='#7c3aed' stroke-width='1'/></g>"""
        elif 'factory' in lowt:
            motif = """<g><rect x='48' y='40' width='160' height='90' rx='8' fill='#fff' stroke='#fb923c'/></g>"""
        else:
            # fallback motif based on category
            if category and category.lower().startswith('crea'):
                motif = """<g><rect x='40' y='60' width='120' height='80' rx='8' fill='#fff6f7' stroke='#fb923c' stroke-width='2'/></g>"""
            elif category and category.lower().startswith('struc'):
                motif = """<g><rect x='40' y='60' width='120' height='80' rx='8' fill='#f8fbff' stroke='#2563eb' stroke-width='2'/></g>"""
            else:
                motif = """<g><rect x='40' y='60' width='80' height='50' rx='8' fill='#fff' stroke='#1e293b' stroke-width='1.5'/></g>"""

        svg = f"""<?xml version='1.0' encoding='utf-8'?>
<svg xmlns='http://www.w3.org/2000/svg' width='{w}' height='{h}' viewBox='0 0 {w} {h}'>
  <rect width='100%' height='100%' fill='#f8fafc' rx='12'/>
  <g transform='translate(0,12)'>{motif}</g>
  <text x='24' y='{h-28}' font-family='Merriweather, Georgia, serif' font-size='16' fill='#0f172a'>{text}</text>
</svg>
"""

        # always write svg when regenerating motifs (overwrite placeholders)
        dest.write_text(svg, encoding='utf-8')
        return True
    except Exception:
        return False


def collect_patterns():
    categories = {'Behavioral': [], 'Creational': [], 'Structural': []}
    if not PATTERN_ROOT.exists():
        print('Pattern root not found:', PATTERN_ROOT)
        return categories
    for cat_dir in PATTERN_ROOT.iterdir():
        if not cat_dir.is_dir():
            continue
        key = cat_dir.name.lower()
        cat = None
        for k, v in CATEGORY_MAP.items():
            if k in key:
                cat = v
                break
        if not cat:
            if 'behavior' in key: cat = 'Behavioral'
            elif 'creational' in key or 'creationaldesign' in key: cat = 'Creational'
            elif 'structural' in key: cat = 'Structural'
        if not cat:
            continue
        for item in cat_dir.iterdir():
            if not item.is_dir():
                continue
            title = item.name.replace('-', ' ').replace('_', ' ').strip()
            pr = item / 'PatternReference.md'
            if pr.exists():
                content = pr.read_text(encoding='utf-8')
                for line in content.splitlines():
                    line=line.strip()
                    if not line: continue
                    if line.startswith('#'):
                        title = line.lstrip('#').strip()
                    else:
                        title = line
                    break
            summary = read_summary(item)
            title = strip_interview_suffix(title)
            summary = strip_interview_suffix(summary)
            url = f"All_Design_Patterns_{slugify(title)}.html"
            href = None
            for candidate in ('README.md', 'PatternReference.md'):
                p = item / candidate
                if p.exists():
                    href = os.path.relpath(p, ROOT)
                    break
            if not href:
                href = url
            # image detection
            img = None
            src = None
            preferred_names = ('cover','thumbnail','thumb','diagram','image')
            for name in preferred_names:
                for ext in ('png','jpg','jpeg','svg','gif'):
                    p = item / f"{name}.{ext}"
                    if p.exists():
                        src = p
                        break
                if src: break
            if not src:
                for ext in ('png','jpg','jpeg','svg','gif'):
                    matches = list(item.glob(f'*.{ext}'))
                    if matches:
                        src = matches[0]
                        break
            if src:
                PATTERN_IMG_DIR.mkdir(parents=True, exist_ok=True)
                dest = PATTERN_IMG_DIR / (slugify(title) + src.suffix)
                try:
                    shutil.copyfile(str(src), str(dest))
                    img = os.path.relpath(dest, ROOT).replace(os.sep, '/')
                except Exception:
                    img = os.path.relpath(src, ROOT).replace(os.sep, '/')
            if not img:
                PATTERN_IMG_DIR.mkdir(parents=True, exist_ok=True)
                gen_path = PATTERN_IMG_DIR / (slugify(title) + '.svg')
                ok = ensure_svg_for_pattern(title, cat, gen_path)
                if ok:
                    img = os.path.relpath(gen_path, ROOT).replace(os.sep, '/')
                else:
                    img = os.path.join('tools', 'pattern_placeholder.svg')

            # Prefer png variants if available (responsive) — check png folder
            png_dir = PATTERN_IMG_DIR / 'png'
            small_png = png_dir / (slugify(title) + '.png')
            large_png = png_dir / (slugify(title) + '@2x.png')
            if small_png.exists() and large_png.exists():
                img = os.path.relpath(small_png, ROOT).replace(os.sep, '/')
                srcset = f"{os.path.relpath(small_png, ROOT).replace(os.sep, '/')} 320w, {os.path.relpath(large_png, ROOT).replace(os.sep, '/')} 800w"
                sizes = '(max-width: 600px) 320px, 800px'
            else:
                srcset = None
                sizes = None
            categories[cat].append({'title': title, 'summary': summary, 'folder': os.path.relpath(item, ROOT), 'url': url, 'href': href, 'img': img})
    return categories


def write_category_page(cat_name, items):
    out = OUT / f'All_Design_Patterns_{cat_name}.html'
    with out.open('w', encoding='utf-8') as f:
        # build nav with active class for current category
        nav_items = [
            ('Creational', 'All_Design_Patterns_Creational.html'),
            ('Structural', 'All_Design_Patterns_Structural.html'),
            ('Behavioral', 'All_Design_Patterns_Behavioral.html')
        ]
        nav_html = ['<ul class="cat-list">']
        for name, href in nav_items:
            cls = 'cat-btn'
            if name == cat_name:
                cls += ' active'
            nav_html.append(f'<li><a class="{cls}" href="{href}" data-cat="{name}">{name}</a></li>')
        nav_html.append('</ul>')
        head = TEMPLATE_HEAD.replace('{title}', f"{cat_name} Patterns").replace('[[NAV]]', '\n'.join(nav_html))
        f.write(head)
        f.write('\n      <section class="panel">\n        <div class="pattern-list-grid">\n')
        for it in items:
            # link card to the generated HTML page for the pattern
            </head>
                        f.write('          <article class="card" data-cat="'+cat_name+'" tabindex="0" data-href="'+html.escape(it.get('url',''))+'">\n')
                        imgsrc = html.escape(it.get('img',''))
                        slug = slugify(it['title'])
</head>
<body>
    <main style="max-width:900px;margin:28px auto;padding:16px;">
        <article id="content" class="markdown-body"></article>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/dompurify/2.4.0/purify.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.8.0/highlight.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"></script>
    <script>
        (function(){
            const md = JSON.parse(%(md_json)s);
            const raw = md || '';
            const rendered = marked.parse(raw);
            const safe = DOMPurify.sanitize(rendered, {ADD_ATTR: ['target']});
            const el = document.getElementById('content');
            el.innerHTML = safe;
            document.querySelectorAll('pre code').forEach((b)=>{hljs.highlightElement(b)});
            try{ if(window.mermaid) mermaid.init(undefined, document.querySelectorAll('.language-mermaid, .mermaid')); }catch(e){}
        })();
    </script>
</body>
</html>'''
                                page_html = page_template % {'title': it['title'], 'md_json': json.dumps(md_text)}
                                try:
                                        pattern_page.write_text(page_html, encoding='utf-8')
                                except Exception:
                                        pass
                        except Exception:
                                pass
            svg_file = Path('tools') / 'pattern_images' / (slug + '.svg')
            png_small = Path('tools') / 'pattern_images' / 'png' / (slug + '.png')
            png_large = Path('tools') / 'pattern_images' / 'png' / (slug + '@2x.png')

            # Prefer SVG via <picture> when available, fall back to responsive PNGs
            if svg_file.exists() and png_small.exists() and png_large.exists():
                srcset_attr = f'{png_small.as_posix()} 320w, {png_large.as_posix()} 800w'
                sizes_attr = '(max-width: 600px) 320px, 800px'
                f.write('            <div class="thumb-wrap">')
                f.write(f'<picture><source srcset="{svg_file.as_posix()}" type="image/svg+xml">')
                f.write(f'<img class="thumb" src="{png_small.as_posix()}" srcset="{srcset_attr}" sizes="{sizes_attr}" alt="'+html.escape(it.get('title',''))+' image"></picture>')
                f.write('</div>\n')
            elif png_small.exists() and png_large.exists():
                srcset_attr = f'{png_small.as_posix()} 320w, {png_large.as_posix()} 800w'
                sizes_attr = '(max-width: 600px) 320px, 800px'
                f.write('            <div class="thumb-wrap"><img class="thumb" src="'+png_small.as_posix()+'" srcset="'+srcset_attr+'" sizes="'+sizes_attr+'" alt="'+html.escape(it.get('title',''))+' image"></div>\n')
            elif svg_file.exists():
                f.write('            <div class="thumb-wrap"><img class="thumb" src="'+svg_file.as_posix()+'" alt="'+html.escape(it.get('title',''))+' image"></div>\n')
            else:
                f.write('            <div class="thumb-wrap"><img class="thumb" src="'+html.escape(imgsrc)+'" alt="'+html.escape(it.get('title',''))+' image"></div>\n')
            f.write('            <h3>'+html.escape(it['title'])+'</h3>\n')
            if it['summary']:
                f.write('            <div class="intent">'+it['summary']+'</div>\n')
            else:
                f.write('            <div class="intent">No summary available.</div>\n')
            f.write('          </article>\n')
        f.write('        </div>\n      </section>\n')
        f.write(TEMPLATE_FOOT)
    print('Wrote', out)


def main():
    cats = collect_patterns()
    for k, items in cats.items():
        write_category_page(k, items)
    print('Done')


if __name__ == '__main__':
    main()



