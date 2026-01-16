#!/usr/bin/env python3
from pathlib import Path
import re

base = Path('LLD/DesignPattern')

# simple templates per pattern (best-effort)
templates = {
    'factoryPattern': {
        'without': ('// Without Factory: client instantiates concrete types directly\n'
                    'Shape s = new Circle();\n'
                    's.draw();'),
        'with': ('// With Factory: use ShapeFactory to obtain interface\n'
                 'Shape s = ShapeFactory.getShape("circle");\n'
                 's.draw();')
    },
    'singletonDesignPattern': {
        'without': ('// Without Singleton: using public static field or multiple instances\n'
                    'Config a = new Config();\n'
                    'Config b = new Config();'),
        'with': ('// With Singleton: single shared instance\n'
                 'Config cfg = Config.getInstance();')
    },
    'strategyPattern': {
        'without': ('// Without Strategy: conditional selection of algorithm\n'
                    'if(mode=="zip") compressZip(data); else compressGzip(data);'),
        'with': ('// With Strategy: inject Compression strategy\n'
                 'Compressor c = new Compressor(new ZipCompression());\n'
                 'c.compress(data);')
    },
    'ObserverPattern': {
        'without': ('// Without Observer: manual callback invocations across components\n'
                    'serviceChanged(); loggerUpdate(); cacheUpdate();'),
        'with': ('// With Observer: register observers to subject\n'
                 'subject.register(new LoggerObserver());\n'
                 'subject.register(new CacheObserver());\n'
                 'subject.notifyAll("UPDATE");')
    }
}

def find_all_pattern_refs():
    refs = list(Path('LLD').rglob('PatternReference.md'))
    return refs

def ensure_contrast(path: Path):
    txt = path.read_text(encoding='utf-8')
    if 'Without Pattern' in txt or 'With Pattern' in txt:
        return False
    name = path.parent.name
    key = name
    # try common name variations
    if key not in templates:
        # lowercase with no spaces
        key = ''.join(ch for ch in name if ch.isalnum())
    tpl = templates.get(key)
    without = '```java\n// Example not provided.\n```'
    withp = '```java\n// Example not provided.\n```'
    if tpl:
        without = '```java\n' + tpl['without'] + '\n```'
        withp = '```java\n' + tpl['with'] + '\n```'
    insert = '\n## Without Pattern\n\n' + without + '\n\n## With Pattern\n\n' + withp + '\n\n'
    # try to insert after Minimal Java Example or before Advantages
    if '## Minimal Java Example' in txt:
        txt = txt.replace('## Minimal Java Example', '## Minimal Java Example' + insert)
    else:
        # append near the start after Intent
        m = re.search(r'(## Intent[\s\S]{0,400}?)\n\n', txt)
        if m:
            idx = m.end()
            txt = txt[:idx] + insert + txt[idx:]
        else:
            txt = txt + insert
    path.write_text(txt, encoding='utf-8')
    return True

if __name__ == '__main__':
    refs = find_all_pattern_refs()
    created = 0
    for r in refs:
        if ensure_contrast(r):
            print('Updated', r)
            created += 1
    print('Inserted contrasts into', created, 'files')
