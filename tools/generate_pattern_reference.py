#!/usr/bin/env python3
import re
from pathlib import Path

base = Path('LLD/DesignPattern')
categories = ['behavioralDesign','creationalDesign','StructuralDesign']

template = lambda name, overview, code: f'''# {name} — Interview Reference

## Intent
Provide a concise intent for the {name} pattern.

## Problem Statement
{overview}

## Why Simple Code Fails
Often ad-hoc solutions (if/else, scattered constructors, tight coupling) make code hard to extend and test.

## Solution Overview
Describe how the {name} pattern solves the problem by providing structure and separation of concerns.

## Minimal Java Example
{code}

## Advantages
- Describe key advantages (decoupling, extensibility, reuse).

## Disadvantages
- Describe trade-offs (complexity, indirection, overuse).

## When NOT to Use
- Situations where the pattern is unnecessary.

## Common Mistakes
- Frequent anti-patterns or pitfalls.

## Framework / Library Usage
- Notes on common frameworks or language features.

## System Design Use Cases
- Real-world systems where this pattern helps.

## Interview One-Liner
One-line summary of the pattern.

'''

def extract_overview(text):
    # take first paragraph after heading
    parts = re.split(r'\n\s*\n', text.strip())
    for p in parts:
        if len(p.strip())>20:
            return p.strip().replace('\n',' ')
    return 'No overview available.'

def extract_java_block(text):
    m = re.search(r'```java\n([\s\S]*?)\n```', text)
    if m:
        return '```java\n'+m.group(1).strip()+"\n```"
    return '```java\n// Example not provided in original README.\n```'

created = 0
for cat in categories:
    catpath = base / cat
    if not catpath.exists():
        continue
    for d in sorted([p for p in catpath.iterdir() if p.is_dir()]):
        pref = d / 'PatternReference.md'
        if pref.exists():
            continue
        readme = d / 'README.md'
        overview = 'No overview available.'
        code = '```java\n// Example not available.\n```'
        if readme.exists():
            txt = readme.read_text(encoding='utf-8')
            overview = extract_overview(txt)
            code = extract_java_block(txt)
        content = template(d.name, overview, code)
        pref.write_text(content, encoding='utf-8')
        print('Created', pref)
        created += 1

print('PatternReference created for', created, 'patterns')
