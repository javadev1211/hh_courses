package jlogic;

/* ====================================================================
 * Copyright (c) 2003 Daniel F. Savarese.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by
 *        Daniel F. Savarese (http://www.savarese.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Daniel F. Savarese" and "Daniel Savarese" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission.  For written
 *    permission, please contact licensing at savarese.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DANIEL F. SAVARESE BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
/* Substitution.java,v 1.3 2003/04/16 04:13:43 dfs Exp */

import java.util.*;

public class Substitution {

  public static class Element {
    private Term term;
    private Variable var;

    public Element(Term term, Variable var) {
      this.term = term;
      this.var  = var;
    }

    public Term getTerm() { return term; }

    public Variable getVariable() { return var; }

    public String toString() {
      return term.toString() + " / " + var.toString();
    }

    public Element substitute(Substitution sub) {
      return new Element((Term)term.substitute(sub), var);
    }
  }

  ArrayList substitutions;

  public Substitution() {
    substitutions = new ArrayList();
  }

  public void add(Element element) {
    substitutions.add(element);
  }

  public Iterator iterator() {
    return substitutions.iterator();
  }

  public Term findTerm(String varName) {
    Iterator it = iterator();

    while(it.hasNext()) {
      Element element = (Element)it.next();

      if(varName.equals(element.getVariable().getName()))
        return element.getTerm();
    }

    return null;
  }

  public boolean contains(Variable var) {
      return (findTerm(var.getName()) != null);
  }

  public Substitution compose(Substitution sub) {
    Substitution result;
    Iterator it;
    Element element;

    if(sub == null)
      return this;

    result = new Substitution();
    it = iterator();

    while(it.hasNext()) {
      element = (Element)it.next();
      result.add(element.substitute(sub));
    }

    it = sub.iterator();

    while(it.hasNext()) {
      element = (Element)it.next();

      if(!contains(element.getVariable()))
        result.add(element);
    }

    return result;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    Iterator it = iterator();

    while(it.hasNext()) {
      buffer.append(it.next().toString());
      buffer.append("\n");
    }

    return buffer.toString();
  }
}