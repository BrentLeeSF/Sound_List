package project1;


public class Link {

		/*----------------------------------------------------- */
		/*  Private Data Members -- Link                        */ 
		/*----------------------------------------------------- */

		private Object element;
		private Link nexty;
		private Link nextx;
		private Link current;
		float elem;

		
		public Link(float elem, Link nextx, Link nexty) {
			this.elem = elem;
			nextx = nexty = null;
		}

		public float elem() {
			return elem;
		}

		void setElement(Object element) {
			this.element = element;
		}
		
		Object element() {
			return element;
		}
		
		public void setElem(float elem) {
			this.elem = elem;
		}
		
		public float[] next() {
			return null;
		}
		
		public void setNextX(Link nextx) {
			this.nextx = nextx;
		}	
		
		public Link getNextX() {
			return nextx;
		}
		
		public Link getNextY() {
			return nexty;
		}
		
		public void setNextY(Link nexty) {
			this.nexty = nexty;
		}

		public boolean hasNextY() {
			return current.getNextY() != null;
		}

		public boolean hasNextX() {
			return current.getNextX() != null;
		}
	}
