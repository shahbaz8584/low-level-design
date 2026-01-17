#!/usr/bin/env python3
import re
from pathlib import Path

base = Path('LLD/DesignPattern')
files = list(base.glob('**/UML/ClassDiagram.md'))
print('Found', len(files), 'UML files')

for p in files:
    text = p.read_text(encoding='utf-8')
    orig = text
    # Remove leading ````markdown wrapper if present
    text = re.sub(r'^```+markdown\n', '', text)
    # Remove trailing ```` if present at end
    text = re.sub(r'\n```+$', '\n```', text)

    # Find all mermaid blocks
    def clean_block(m):
        content = m.group(1)
        # Replace HTML entities inside (if any)
        content = content.replace('&lt;', '<').replace('&gt;', '>').replace('&amp;', '&')
        # Remove stray characters that break mermaid: '*' and '$'
        content = content.replace('*', '').replace('$', '')
        # Also remove repeated spaces at line ends
        content = '\n'.join([ln.rstrip() for ln in content.splitlines()])
        return '```mermaid\n' + content + '\n```'

    text = re.sub(r'```mermaid\n([\s\S]*?)\n```', clean_block, text, flags=re.MULTILINE)

    if text != orig:
        p.write_text(text, encoding='utf-8')
        print('Sanitized', p)
print('Done')
