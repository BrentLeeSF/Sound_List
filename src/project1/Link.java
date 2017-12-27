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
		
		public void setElem(float elem) {
			this.elem = elem;
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
		
		public void setNextX(Link nextx) {
			this.nextx = nextx;
		}
		
		public Link getNextX() {
			return nextx;
		}
		
		public void setNextY(Link nexty) {
			this.nexty = nexty;
		}
		
		public Link getNextY() {
			return nexty;
		}
		
		public float[] next() {
			return null;
		}

		public boolean hasNextX() {
			return current.getNextX() != null;
		}
		
		public boolean hasNextY() {
			return current.getNextY() != null;
		}
		
	}


