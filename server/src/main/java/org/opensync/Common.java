package org.opensync;

public class Common {

	public enum Status {
		OK(1),
		NOOK(0);

		private final int value;

		private Status(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}
	
	
	public enum ConflictHandling {

		TIMESTAMPPRIORITY(1),		// Conflict handling: version with most recent timestamp_lastupdate is used
		SERVERPRIORITY(2),// Conflict handling: version from server is used
		CLIENTPRIORITY(3); // Conflict handling: version from client is used

		


		private final int value;

		private ConflictHandling(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	

}
