/**
 * Pattern Page Renderer
 * Handles markdown parsing, table of contents generation, and syntax highlighting
 */

(function() {
  'use strict';

  /**
   * Initialize pattern page rendering
   */
  function initPatternPage() {
    console.log('[Pattern Renderer] Initializing pattern page...');
    const mdEl = document.getElementById('md-data');
    
    // Parse markdown content
    let md = '';
    if (mdEl) {
      try {
        md = JSON.parse(mdEl.textContent);
      } catch (e) {
        md = mdEl.textContent;
      }
    }

    // Render markdown to HTML
    const raw = md || '';
    const rendered = marked.parse(raw);
    const safe = DOMPurify.sanitize(rendered, { ADD_ATTR: ['target'] });
    
    // Insert rendered content
    const contentEl = document.getElementById('content');
    if (contentEl) {
      console.log('[Pattern Renderer] Rendering markdown to HTML...');
      contentEl.innerHTML = safe;
      // Convert Q&A sections to accordions
      console.log('[Pattern Renderer] Calling convertQAToAccordions...');
      convertQAToAccordions(contentEl);
    }

    // Generate table of contents from headings
    generateTableOfContents();

    // Apply syntax highlighting
    applySyntaxHighlighting();

    // Mermaid diagrams are auto-rendered by startOnLoad: true

    // Add smooth scroll behavior to TOC links
    addTOCScrollBehavior();

    // Q&A accordions are initialized in convertQAToAccordions()
    // Do NOT call initQAAccordions() as it conflicts with the detailed handler
  }

  /**
   * Convert Q&A sections to accordion markup
   */
  function convertQAToAccordions(contentEl) {
    console.log('[QA Accordion] Starting conversion...');
    
    // Find all H3 elements that match Q# pattern
    const allH3s = Array.from(contentEl.querySelectorAll('h3'));
    const qItems = allH3s.filter(h3 => /^Q\d+:/.test(h3.textContent));
    
    console.log('[QA Accordion] Found', qItems.length, 'Q items');
    
    if (qItems.length === 0) {
      console.log('[QA Accordion] No Q items found');
      return;
    }
    
    // Collect data for each Q item BEFORE removing anything
    const qData = [];
    qItems.forEach((qHeading) => {
      const content = [];
      let currentEl = qHeading.nextElementSibling;
      
      // Collect all siblings until next H3 or H2
      while (currentEl && currentEl.tagName !== 'H2' && currentEl.tagName !== 'H3') {
        content.push(currentEl.cloneNode(true));
        currentEl = currentEl.nextElementSibling;
      }
      
      qData.push({
        title: qHeading.textContent,
        content: content,
        heading: qHeading
      });
    });
    
    console.log('[QA Accordion] Collected data for', qData.length, 'questions');
    
    // Create accordion container
    const qaSection = document.createElement('div');
    qaSection.className = 'qa-section';
    
    // Create accordion items
    qData.forEach((item, idx) => {
      console.log('[QA Accordion] Creating item', idx + 1);
      
      const qaItem = document.createElement('div');
      qaItem.className = 'qa-item';
      
      const qaQuestion = document.createElement('div');
      qaQuestion.className = 'qa-question';
      qaQuestion.style.cursor = 'pointer';
      qaQuestion.style.display = 'flex';
      qaQuestion.style.justifyContent = 'space-between';
      qaQuestion.style.alignItems = 'center';
      qaQuestion.style.padding = '1rem';
      qaQuestion.style.backgroundColor = '#e3f2fd';
      qaQuestion.style.border = '1px solid #90caf9';
      qaQuestion.style.borderRadius = '4px';
      qaQuestion.style.marginBottom = '0.5rem';
      
      const questionText = document.createElement('span');
      questionText.textContent = item.title;
      
      const toggle = document.createElement('span');
      toggle.className = 'qa-toggle';
      toggle.textContent = '+';
      toggle.style.fontSize = '1.5rem';
      toggle.style.fontWeight = 'bold';
      toggle.style.minWidth = '30px';
      toggle.style.textAlign = 'center';
      toggle.style.transition = 'transform 0.3s ease';
      
      qaQuestion.appendChild(questionText);
      qaQuestion.appendChild(toggle);
      
      const qaAnswer = document.createElement('div');
      qaAnswer.className = 'qa-answer';
      qaAnswer.style.maxHeight = '0';
      qaAnswer.style.overflow = 'hidden';
      qaAnswer.style.transition = 'max-height 0.3s ease, padding 0.3s ease';
      qaAnswer.style.padding = '0 1.5rem';
      
      // Add content
      item.content.forEach(el => {
        qaAnswer.appendChild(el);
      });
      
      // Click handler
      qaQuestion.addEventListener('click', function() {
        const isOpen = qaItem.classList.contains('open');
        console.log('[QA Click] Clicked:', item.title, '- Currently open:', isOpen);
        console.log('[QA Click] qaItem classes before:', qaItem.className);
        
        // Close ALL other open items FIRST
        document.querySelectorAll('.qa-item.open').forEach(other => {
          if (other === qaItem) return; // Skip current item
          
          console.log('[QA Click] Closing other item');
          other.classList.remove('open');
          const otherBtn = other.querySelector('.qa-toggle');
          const otherAns = other.querySelector('.qa-answer');
          
          if (otherBtn) {
            otherBtn.textContent = '+';
            otherBtn.style.transform = 'rotate(0deg)';
          }
          if (otherAns) {
            otherAns.style.maxHeight = '0';
            otherAns.style.padding = '0 1.5rem';
          }
        });
        
        // Now toggle the current item
        if (isOpen) {
          // Close it
          console.log('[QA Click] Closing current item');
          qaItem.classList.remove('open');
          toggle.textContent = '+';
          toggle.style.transform = 'rotate(0deg)';
          qaAnswer.style.maxHeight = '0';
          qaAnswer.style.padding = '0 1.5rem';
          console.log('[QA Click] After close - classes:', qaItem.className);
        } else {
          // Open it
          console.log('[QA Click] Opening current item');
          qaItem.classList.add('open');
          console.log('[QA Click] After add open - classes:', qaItem.className);
          toggle.textContent = '−';
          toggle.style.transform = 'rotate(180deg)';
          qaAnswer.style.padding = '1.5rem';
          
          // Use setTimeout to ensure DOM is ready for scrollHeight calculation
          setTimeout(() => {
            console.log('[QA Click] In setTimeout - scrollHeight:', qaAnswer.scrollHeight);
            qaAnswer.style.maxHeight = qaAnswer.scrollHeight + 'px';
            console.log('[QA Click] Set maxHeight to:', qaAnswer.scrollHeight + 'px');
          }, 0);
        }
      });
      
      qaItem.appendChild(qaQuestion);
      qaItem.appendChild(qaAnswer);
      qaSection.appendChild(qaItem);
    });
    
    // Now remove ALL original Q headings and their content
    console.log('[QA Accordion] Removing original Q headings from DOM');
    qData.forEach((item) => {
      // Remove all siblings first
      let el = item.heading.nextElementSibling;
      while (el && el.tagName !== 'H2' && el.tagName !== 'H3') {
        const next = el.nextElementSibling;
        el.remove();
        el = next;
      }
      // Then remove the heading itself
      item.heading.remove();
    });
    
    // Find the "Common Interview Questions" h2 heading (it should still exist)
    let ciHeading = null;
    const h2s = contentEl.querySelectorAll('h2');
    for (let h2 of h2s) {
      if (h2.textContent.includes('Common Interview Questions')) {
        ciHeading = h2;
        break;
      }
    }
    
    // Insert accordion AFTER the "Common Interview Questions" heading
    if (qData.length > 0 && ciHeading) {
      ciHeading.parentNode.insertBefore(qaSection, ciHeading.nextSibling);
      console.log('[QA Accordion] Inserted accordion after Common Interview Questions heading');
    } else if (qData.length > 0) {
      // Fallback: append to content if heading not found
      contentEl.appendChild(qaSection);
      console.log('[QA Accordion] Appended accordion to content (fallback)');
    }
    
    console.log('[QA Accordion] Conversion complete');
  }

  /**
   * Initialize Q&A accordion functionality
   */
  function initQAAccordions() {
    const qaQuestions = document.querySelectorAll('.qa-question');
    
    qaQuestions.forEach(question => {
      question.addEventListener('click', function() {
        const qaItem = this.closest('.qa-item');
        const isOpen = qaItem.classList.contains('open');
        
        if (qaItem) {
          qaItem.classList.toggle('open');
          
          // Update ARIA attributes
          this.setAttribute('aria-expanded', !isOpen);
          const answer = qaItem.querySelector('.qa-answer');
          if (answer) {
            answer.setAttribute('aria-hidden', isOpen);
          }
        }
      });

      // Allow keyboard navigation (Enter/Space)
      question.addEventListener('keypress', function(e) {
        if (e.key === 'Enter' || e.key === ' ') {
          e.preventDefault();
          this.click();
        }
      });
    });
  }

  /**
   * Generate table of contents from h2 and h3 headings
   */
  function generateTableOfContents() {
    const contentEl = document.getElementById('content');
    if (!contentEl) return;

    const headings = contentEl.querySelectorAll('h2, h3');
    const tocList = document.getElementById('toc-list');
    
    if (!tocList) return;

    headings.forEach((heading, idx) => {
      const level = parseInt(heading.tagName[1]);
      const id = 'heading-' + idx;
      heading.id = id;
      const headingText = heading.textContent;

      // Create TOC entry
      const li = document.createElement('li');
      const a = document.createElement('a');
      a.href = '#' + id;
      a.textContent = headingText;
      a.style.paddingLeft = (level - 2) * 1 + 'rem';
      
      // All items use consistent styling
      if (level === 2) {
        a.style.fontWeight = '600';
        a.style.color = '#1f6feb';
      }
      
      li.appendChild(a);
      tocList.appendChild(li);
    });
  }

  /**
   * Add smooth scroll behavior to TOC links
   */
  function addTOCScrollBehavior() {
    const tocLinks = document.querySelectorAll('#toc-list a');
    
    tocLinks.forEach(link => {
      link.addEventListener('click', function(e) {
        e.preventDefault();
        const targetId = this.getAttribute('href').substring(1);
        const targetEl = document.getElementById(targetId);
        
        if (targetEl) {
          targetEl.scrollIntoView({ behavior: 'smooth', block: 'start' });
          // Update active state
          tocLinks.forEach(l => l.style.borderLeftColor = 'transparent');
          this.style.borderLeftColor = '#1f6feb';
        }
      });
    });

    // Update active TOC link on scroll
    window.addEventListener('scroll', updateActiveTOC);
  }

  /**
   * Update active TOC link based on scroll position
   */
  function updateActiveTOC() {
    const headings = document.querySelectorAll('#content h2, #content h3');
    const tocLinks = document.querySelectorAll('#toc-list a');
    
    let activeHeading = null;
    
    headings.forEach(heading => {
      const rect = heading.getBoundingClientRect();
      if (rect.top <= 100) {
        activeHeading = heading;
      }
    });

    tocLinks.forEach(link => {
      link.style.borderLeftColor = 'transparent';
    });

    if (activeHeading) {
      const activeId = activeHeading.id;
      const activeLink = document.querySelector(`#toc-list a[href="#${activeId}"]`);
      if (activeLink) {
        activeLink.style.borderLeftColor = '#1f6feb';
      }
    }
  }

  /**
   * Apply syntax highlighting to code blocks
   */
  function applySyntaxHighlighting() {
    document.querySelectorAll('pre code').forEach((block) => {
      hljs.highlightElement(block);
    });
  }

  // Mermaid is handled by startOnLoad: true in HTML initialization
  // No manual diagram rendering needed

  // Initialize when DOM is ready
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initPatternPage);
  } else {
    initPatternPage();
  }
})();
