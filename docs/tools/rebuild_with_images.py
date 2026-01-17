#!/usr/bin/env python3
import os
from pathlib import Path
import re
base = Path('LLD/DesignPattern')
out = Path('All_Design_Patterns.md')
img_dir = Path('build/diagrams')

def safe_name(p):
    rel = str(p.relative_to(base))
    #!/usr/bin/env python3
    import os
    #!/usr/bin/env python3
    import os
    from pathlib import Path
    import re

    base = Path('LLD/DesignPattern')
    out = Path('All_Design_Patterns.md')
    img_dir = Path('build/diagrams')


    def safe_name(p):
        rel = str(p.relative_to(base))
        s = rel.replace(os.sep, '_')
        s = ''.join(c for c in s if c.isalnum() or c in '._-')
        return s


    def first_java_example(dirpath):
        for p in dirpath.rglob('*.java'):
            try:
                return p.read_text(encoding='utf-8')
            except Exception:
                continue
        return None


    def main():
        with open(out, 'w', encoding='utf-8') as o:
            o.write('# All Design Patterns\n\n')
            o.write('Generated design-pattern handbook — interview-ready.\n\n')

            o.write('## What is a Design Pattern?\n\n')
            o.write(
                'A design pattern is a reusable solution to a commonly occurring problem within a given context in software design. Patterns provide templates for how to solve problems that arise frequently, improving communication, maintainability, and design quality.\n\n')

            o.write('## Why Use Design Patterns?\n\n')
            o.write('- Capture proven best-practices and trade-offs.\n- Improve readability by using standard vocabulary.\n- Promote decoupling, extensibility and testability.\n\n')

            o.write('## Categories\n\n')
            o.write('- Creational Patterns — deal with object creation mechanisms.\n- Structural Patterns — compose classes and objects to form larger structures.\n- Behavioral Patterns — manage communication between objects.\n\n')

            categories = ['behavioralDesign', 'creationalDesign', 'StructuralDesign']
            for cat in categories:
                catpath = base / cat
                if not catpath.exists():
                    continue

                print('Processing category:', cat)

                if cat == 'behavioralDesign':
                    o.write('\n---\n\n# Behavioral Patterns\n\nProblem domain: how objects interact and distribute responsibility.\n\n')
                elif cat == 'creationalDesign':
                    o.write('\n---\n\n# Creational Patterns\n\nProblem domain: object creation mechanisms and lifecycle.\n\n')
                elif cat == 'StructuralDesign':
                    o.write('\n---\n\n# Structural Patterns\n\nProblem domain: composing objects and classes for larger structures.\n\n')

                for name in sorted([d.name for d in catpath.iterdir() if d.is_dir()]):
                    d = catpath / name
                    print('  pattern:', name)
                    o.write('\n---\n\n')
                    o.write(f'## {name}\n\n')

                    pref = d / 'PatternReference.md'
                    readme = d / 'README.md'
                    uml = d / 'UML' / 'ClassDiagram.md'

                    # High level summary
                    if pref.exists():
                        o.write(pref.read_text(encoding='utf-8'))
                        o.write('\n\n')
                    elif readme.exists():
                        o.write('### Overview (README.md)\n\n')
                        o.write(readme.read_text(encoding='utf-8'))
                        o.write('\n\n')
                    else:
                        o.write('_No description available._\n\n')

                    # Structured subsections the user requested
                    o.write('### Definition\n\n')
                    o.write('_Short definition or summary (to add)._\n\n')

                    o.write('### Why Use It / Motivation\n\n')
                    o.write('_Describe when and why this pattern is useful._\n\n')

                    o.write('### Problem It Solves\n\n')
                    o.write('_Concrete problem statement the pattern addresses._\n\n')

                    o.write('### Solution Overview\n\n')
                    o.write('_High-level solution description and key participants._\n\n')

                    o.write('### Advantages\n\n')
                    o.write('- _List advantages._\n\n')

                    o.write('### Disadvantages / Trade-offs\n\n')
                    o.write('- _List disadvantages._\n\n')

                    o.write('### Similar / Related Patterns\n\n')
                    o.write('_Comparison notes vs related patterns._\n\n')

                    # Minimal Java Example section with explicit Without/With subsections
                    java_example = first_java_example(d)
                    o.write('### Minimal Java Example\n\n')
                    o.write('#### Without Pattern\n\n')
                    if java_example:
                        # show the same example as a minimal snippet
                        o.write('```java\n')
                        o.write(java_example)
                        o.write('\n```\n\n')
                    else:
                        o.write('```java\n')
                        o.write('// Example not provided.\n')
                        o.write('```\n\n')

                    o.write('#### With Pattern\n\n')
                    # Provide a placeholder demonstrating how the pattern would be used
                    o.write('```java\n')
                    o.write('// Java example placeholder for {} (with pattern applied)\n'.format(name))
                    o.write('public class {}Example {{\n'.format(name.replace('-', '').replace(' ', '')))
                    o.write('  public static void main(String[] args) {{\n')
                    o.write('    // TODO: add example demonstrating the {} pattern\n'.format(name))
                    o.write('  }\n')
                    o.write('}\n')
                    o.write('```\n\n')

                    # Backwards-compatible generic Example section
                    o.write('### Example (Java)\n\n')
                    if java_example:
                        o.write('```java\n')
                        o.write(java_example)
                        o.write('\n```\n\n')
                    else:
                        o.write('```java\n')
                        o.write('// Java example placeholder for {}\n'.format(name))
                        o.write('public class {}Example {{\n'.format(name.replace('-', '').replace(' ', '')))
                        o.write('  public static void main(String[] args) {{\n')
                        o.write('    // TODO: add example demonstrating the {} pattern\n'.format(name))
                        o.write('  }\n')
                        o.write('}\n')
                        o.write('```\n\n')

                    # UML inclusion (inline SVG if available)
                    if uml.exists():
                        o.write('### UML / Class Diagram\n\n')
                        imgname = safe_name(uml)
                        svgpath = img_dir / (imgname + '.svg')
                        pngpath = img_dir / (imgname + '.png')
                        if svgpath.exists():
                            svg_text = svgpath.read_text(encoding='utf-8')
                            svg_text = re.sub(r'<\?xml.*?\?>\s*', '', svg_text, flags=re.S)
                            o.write('<figure class="diagram">\n')
                            o.write(svg_text)
                            o.write(f'\n<figcaption>{name} — UML Class Diagram</figcaption>\n</figure>\n\n')
                        elif pngpath.exists():
                            o.write(f'<div class="diagram"><img src="{pngpath.as_posix()}" alt="{name} UML"/></div>\n\n')
                        else:
                            o.write('```mermaid\n')
                            o.write(uml.read_text(encoding='utf-8'))
                            o.write('\n```\n\n')

        print('Rebuilt', out)


    if __name__ == '__main__':
        main()
