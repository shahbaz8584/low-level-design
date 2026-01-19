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
    console.log('[QA Accordion] Starting conversion (native details/summary)...');

    // Find all H3 elements that match Q# pattern
    const allH3s = Array.from(contentEl.querySelectorAll('h3'));
    const qItems = allH3s.filter(h3 => /^Q\d+:/.test(h3.textContent));

    console.log('[QA Accordion] Found', qItems.length, 'Q items');
    if (qItems.length === 0) return;

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

    // Create accordion container
    const qaSection = document.createElement('div');
    qaSection.className = 'qa-section';

    // Create native <details> items
    qData.forEach((item, idx) => {
      const details = document.createElement('details');
      details.className = 'qa-item';

      const summary = document.createElement('summary');
      summary.className = 'qa-question';
      summary.setAttribute('role', 'button');
      summary.setAttribute('aria-expanded', 'false');

      const questionText = document.createElement('span');
      questionText.className = 'qa-question-text';
      questionText.textContent = item.title;

      const toggle = document.createElement('span');
      toggle.className = 'qa-toggle';
      toggle.textContent = '+';

      summary.appendChild(questionText);
      summary.appendChild(toggle);

      const answer = document.createElement('div');
      answer.className = 'qa-answer';

      item.content.forEach(el => answer.appendChild(el));

      // Update toggle icon on open/close
      details.addEventListener('toggle', () => {
        const open = details.hasAttribute('open');
        summary.setAttribute('aria-expanded', open ? 'true' : 'false');
        toggle.textContent = open ? '−' : '+';

        // If opened, close other details to maintain single-open behavior
        if (open) {
          document.querySelectorAll('.qa-item').forEach(other => {
            if (other !== details && other.hasAttribute('open')) {
              other.removeAttribute('open');
            }
          });
        }
      });

      details.appendChild(summary);
      details.appendChild(answer);
      qaSection.appendChild(details);
    });

    // Remove original Q headings and their content
    qData.forEach((item) => {
      let el = item.heading.nextElementSibling;
      while (el && el.tagName !== 'H2' && el.tagName !== 'H3') {
        const next = el.nextElementSibling;
        el.remove();
        el = next;
      }
      item.heading.remove();
    });

    // Insert accordion AFTER the "Common Interview Questions" heading, fallback to append
    let ciHeading = null;
    const h2s = contentEl.querySelectorAll('h2');
    for (let h2 of h2s) {
      if (h2.textContent.includes('Common Interview Questions')) {
        ciHeading = h2;
        break;
      }
    }

    if (ciHeading) ciHeading.parentNode.insertBefore(qaSection, ciHeading.nextSibling);
    else contentEl.appendChild(qaSection);

    console.log('[QA Accordion] Conversion complete (details/summary)');
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
