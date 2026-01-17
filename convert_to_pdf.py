#!/usr/bin/env python3
import subprocess
import os

os.chdir('/Users/shahbazhussain/Documents/low-level-design/low-level-design')

try:
    result = subprocess.run([
        '/opt/homebrew/bin/pandoc',
        'All_Design_Patterns.md',
        '-o',
        'All_Design_Patterns.pdf'
    ], capture_output=True, text=True, timeout=60)
    
    if result.returncode == 0:
        file_size = os.path.getsize('All_Design_Patterns.pdf')
        size_mb = file_size / (1024 * 1024)
        print(f"✅ PDF created successfully!")
        print(f"📄 File: All_Design_Patterns.pdf")
        print(f"📊 Size: {size_mb:.2f} MB")
    else:
        print(f"❌ Error: {result.stderr}")
except Exception as e:
    print(f"❌ Exception: {e}")
