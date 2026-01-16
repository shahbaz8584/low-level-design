#!/usr/bin/env python3
import os
from pathlib import Path
base = Path('LLD/DesignPattern')
out = Path('All_Design_Patterns.md')
img_dir = Path('build/diagrams')

def safe_name(p):
    rel = str(p.relative_to(base))
    s = rel.replace(os.sep,'_')
    s = ''.join(c for c in s if c.isalnum() or c in '._-')
    return s

with open(out,'w',encoding='utf-8') as o:
    o.write('# All Design Patterns\n\nGenerated with rendered UML images where available.\n\n')
    categories=['behavioralDesign','creationalDesign','StructuralDesign']
    for cat in categories:
        catpath = base / cat
        if not catpath.exists():
            continue
        for name in sorted([d.name for d in catpath.iterdir() if d.is_dir()]):
            d = catpath / name
            o.write('\n---\n\n')
            o.write(f'## {name}\n\n')
            readme = d / 'README.md'
            uml = d / 'UML' / 'ClassDiagram.md'
            if readme.exists():
                o.write('### Overview (README.md)\n\n')
                o.write(readme.read_text(encoding='utf-8'))
                o.write('\n\n')
            if uml.exists():
                o.write('### UML / Class Diagram\n\n')
                imgname = safe_name(uml)
                svgpath = img_dir / (imgname + '.svg')
                pngpath = img_dir / (imgname + '.png')
                if svgpath.exists():
                    o.write(f'<div class="diagram"><img src="{svgpath.as_posix()}" alt="{name} UML"/></div>\n\n')
                elif pngpath.exists():
                    o.write(f'<div class="diagram"><img src="{pngpath.as_posix()}" alt="{name} UML"/></div>\n\n')
                else:
                    o.write('```mermaid\n')
                    o.write(uml.read_text(encoding='utf-8'))
                    o.write('\n```\n\n')
print('Rebuilt', out)
