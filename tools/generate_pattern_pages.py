#!/usr/bin/env python3
"""
Generate per-pattern HTML pages that render the pattern Markdown into a readable, responsive page.
Writes files next to the category pages named: All_Design_Patterns_<slug>.html
This script is standalone and does not import the (possibly edited) category generator.
"""
import os
from pathlib import Path
import re
import json

ROOT = Path(__file__).resolve().parents[1]
PATTERN_ROOT = ROOT / 'LLD' / 'DesignPattern'
OUT = ROOT


def slugify(name: str):
    return ''.join(c if c.isalnum() else '-' for c in name).strip('-').lower()


def strip_interview_suffix(text: str):
    if not text:
        return ''
    return re.sub(r"\s*[—-]\s*Interview Reference\b", '', text, flags=re.IGNORECASE).strip()


def title_from_md(folder: Path):
    # prefer PatternReference.md then README.md
    for candidate in ('PatternReference.md', 'README.md'):
        p = folder / candidate
        if p.exists():
            txt = p.read_text(encoding='utf-8')
            for line in txt.splitlines():
                line = line.strip()
                if not line:
                    continue
                if line.startswith('#'):
                    return line.lstrip('#').strip(), p
                return line, p
    return folder.name.replace('-', ' ').replace('_', ' ').strip(), None


def extract_mermaid_diagram(folder: Path):
    """Extract Mermaid diagram from UML/ClassDiagram.md if it exists"""
    uml_file = folder / 'UML' / 'ClassDiagram.md'
    if not uml_file.exists():
        return None
    
    content = uml_file.read_text(encoding='utf-8')
    # Look for mermaid code block
    match = re.search(r'```mermaid\n([\s\S]*?)```', content)
    if match:
        return match.group(1).strip()
    return None


def extract_qa_section(md_text: str):
    """Extract Q&A section from markdown and convert to HTML"""
    # Look for "Common Interview Questions" section
    qa_match = re.search(r'##\s*Common Interview Questions\s*\n([\s\S]*?)(?=##|$)', md_text)
    if not qa_match:
        return ''
    
    qa_text = qa_match.group(1).strip()
    if not qa_text:
        return ''
    
    # Extract questions (usually lines starting with - or bullet points)
    questions = re.findall(r'^[-•]\s*(.+?)$', qa_text, re.MULTILINE)
    
    if not questions:
        return ''
    
    # Build Q&A HTML
    qa_html = '<div class="qa-section"><h2>Common Interview Questions</h2>\n'
    
    for i, question in enumerate(questions):
        # Extract answer (next lines that are indented or are sub-bullets)
        answer = f"A common interview question about this pattern. Click to see best practices and considerations."
        
        qa_html += f'''<div class="qa-item">
    <div class="qa-question" tabindex="0" role="button">
      <span class="qa-question-text">Q: {question}</span>
      <span class="qa-toggle">▼</span>
    </div>
    <div class="qa-answer">
      <p>{answer}</p>
    </div>
  </div>
'''
    
    qa_html += '</div>\n'
    return qa_html


PAGE_TEMPLATE = '''<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>{title} - Design Patterns</title>
  <link rel="stylesheet" href="tools/index_style.css">
  <link rel="stylesheet" href="tools/patterns_responsive.css">
  <link rel="stylesheet" href="tools/pattern_page.css">
  <link rel="stylesheet" href="tools/pattern_layout.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.8.0/styles/github-dark.min.css">
</head>
<body style="background: #f7fafc;">
  <div style="max-width: 1200px; margin: 0 auto; padding: 1.5rem;">
    <a href="All_Design_Patterns_index.html" class="back-button">← Back to Patterns</a>
    
    <div class="pattern-header">
      <div class="breadcrumb">Design Patterns</div>
      <h1>{title}</h1>
    </div>

    <div class="pattern-container">
      <aside class="pattern-toc">
        <h4>Contents</h4>
        <ul id="toc-list"></ul>
      </aside>
      <div class="pattern-main">
        {uml_content}
        <article id="content" class="markdown-body"></article>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/dompurify/2.4.0/purify.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.8.0/highlight.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"></script>
  <script id="md-data" type="application/json">{md_json}</script>
  <script src="tools/pattern_renderer.js"></script>
</body>
</html>'''


def collect_pattern_folders(root: Path):
    out = []
    if not root.exists():
        return out
    for cat_dir in root.iterdir():
        if not cat_dir.is_dir():
            continue
        for item in cat_dir.iterdir():
            if not item.is_dir():
                continue
            out.append(item)
    return out


def main():
    folders = collect_pattern_folders(PATTERN_ROOT)
    for folder in folders:
        title, src = title_from_md(folder)
        title = strip_interview_suffix(title)
        # Use folder name for slug instead of title, so each folder gets unique file
        slug = slugify(folder.name)
        url = f"All_Design_Patterns_{slug}.html"
        md_text = ''
        if src and src.exists():
            md_text = src.read_text(encoding='utf-8')
        
        # Extract UML diagram if available
        uml_diagram = extract_mermaid_diagram(folder)
        uml_content = ''
        if uml_diagram:
            uml_content = f'''<div class="uml-section">
    <h2>Class Diagram</h2>
    <div class="mermaid">{uml_diagram}</div>
    <div class="uml-caption">UML Class Diagram for {title}</div>
  </div>
  '''
        
        page_html = PAGE_TEMPLATE.format(title=title, md_json=json.dumps(md_text), pct='%', uml_content=uml_content)
        try:
            (OUT / url).write_text(page_html, encoding='utf-8')
            print('Wrote', url)
        except Exception as e:
            print('Failed writing', url, e)


if __name__ == '__main__':
    main()
