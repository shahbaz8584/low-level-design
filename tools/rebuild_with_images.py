#!/usr/bin/env python3
import os
from pathlib import Path
import re
base = Path('LLD/DesignPattern')
out = Path('All_Design_Patterns.md')
img_dir = Path('build/diagrams')

def safe_name(p):
    rel = str(p.relative_to(base))
    s = rel.replace(os.sep,'_')
    s = ''.join(c for c in s if c.isalnum() or c in '._-')
    return s

with open(out,'w',encoding='utf-8') as o:
    o.write('# All Design Patterns\n\nGenerated design-pattern handbook — interview-ready.\n\n')
    o.write('## What is a Design Pattern?\n\n')
    o.write('A design pattern is a reusable solution to a commonly occurring problem within a given context in software design. Patterns provide templates for how to solve problems that arise frequently, improving communication, maintainability, and design quality.\n\n')
    o.write('## Why Use Design Patterns?\n\n')
    o.write('- Capture proven best-practices and trade-offs.\n- Improve readability by using standard vocabulary.\n- Promote decoupling, extensibility and testability.\n\n')
    o.write('## Categories\n\n')
    o.write('- Creational Patterns — deal with object creation mechanisms.\n- Structural Patterns — compose classes and objects to form larger structures.\n- Behavioral Patterns — manage communication between objects.\n\n')
    categories=['behavioralDesign','creationalDesign','StructuralDesign']
    for cat in categories:
        catpath = base / cat
        if not catpath.exists():
            continue
        # Category overview header
        if cat == 'behavioralDesign':
            o.write(f'\n---\n\n# Behavioral Patterns\n\nProblem domain: how objects interact and distribute responsibility.\n\n')
        elif cat == 'creationalDesign':
            o.write(f'\n---\n\n# Creational Patterns\n\nProblem domain: object creation mechanisms and lifecycle.\n\n')
        elif cat == 'StructuralDesign':
            o.write(f'\n---\n\n# Structural Patterns\n\nProblem domain: composing objects and classes for larger structures.\n\n')
        for name in sorted([d.name for d in catpath.iterdir() if d.is_dir()]):
            d = catpath / name
            o.write('\n---\n\n')
            o.write(f'## {name}\n\n')
            # prefer PatternReference if present (interview-ready), otherwise README
            pref = d / 'PatternReference.md'
            readme = d / 'README.md'
            uml = d / 'UML' / 'ClassDiagram.md'
            if pref.exists():
                o.write(pref.read_text(encoding='utf-8'))
                o.write('\n\n')
            elif readme.exists():
                o.write('### Overview (README.md)\n\n')
                o.write(readme.read_text(encoding='utf-8'))
                o.write('\n\n')
            else:
                o.write('_No description available._\n\n')
            # Defer UML diagrams until content is finalized — placeholder shown here
            if uml.exists():
                o.write('### UML / Class Diagram (TO DO)\n\n')
                o.write('_UML diagrams will be added after content review._\n\n')
print('Rebuilt', out)
