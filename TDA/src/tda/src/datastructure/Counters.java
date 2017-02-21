package tda.src.datastructure;

/**
 * A representation of the result summary of a {@code TestRun}.
 *
 */
public class Counters {
	String sumAborted;
	String sumCompleted;
	String sumDisconnected;
	String sumError;
	String sumExecuted;
	String sumFailed;
	String sumInProgress;
	String sumInconclusive;
	String sumNotExecuted;
	String sumNotRunnable;
	String sumPassed;
	String sumPassedButRunAborted;
	String sumPending;
	String sumTimeOut;
	String sumTotal;
	String sumWarning;

	/**
	 * Constructor to instantiate an object of type {@code Counters}.
	 * 
	 * @param sumAborted
	 *            - a {@code String} containing the number of aborted tests.
	 * @param sumCompleted
	 *            - a {@code String} containing the number of completed tests.
	 * @param sumDisconnected
	 *            - a {@code String} containing the number of disconnected
	 *            tests.
	 * @param sumError
	 *            - a {@code String} containing the number of tests with errors.
	 * @param sumExecuted
	 *            - a {@code String} containing the number of executed tests.
	 * @param sumFailed
	 *            - a {@code String} containing the number of failed tests.
	 * @param sumInProgress
	 *            - a {@code String} containing the number of tests in progress.
	 * @param sumInconclusive
	 *            - a {@code String} containing the number of inconclusive
	 *            tests.
	 * @param sumNotExecuted
	 *            - a {@code String} containing the number of not executed
	 *            tests.
	 * @param sumNotRunnable
	 *            - a {@code String} containing the number of tests that were
	 *            not runnable.
	 * @param sumPassed
	 *            - a {@code String} containing the number of passed tests.
	 * @param sumPassedButRunAborted
	 *            - a {@code String} containing the number of passed tests that
	 *            were aborted.
	 * @param sumPending
	 *            - a {@code String} containing the number of pending tests.
	 * @param sumTimeOut
	 *            - a {@code String} containing the number of tests that ran out
	 *            of time.
	 * @param sumTotal
	 *            - a {@code String} containing the total number of tests.
	 * @param sumWarning
	 *            - a {@code String} containing the number of tests with
	 *            warnings.
	 */
	public Counters(String sumAborted, String sumCompleted, String sumDisconnected, String sumError, String sumExecuted,
			String sumFailed, String sumInProgress, String sumInconclusive, String sumNotExecuted,
			String sumNotRunnable, String sumPassed, String sumPassedButRunAborted, String sumPending,
			String sumTimeOut, String sumTotal, String sumWarning) {
		super();
		this.sumAborted = sumAborted;
		this.sumCompleted = sumCompleted;
		this.sumDisconnected = sumDisconnected;
		this.sumError = sumError;
		this.sumExecuted = sumExecuted;
		this.sumFailed = sumFailed;
		this.sumInProgress = sumInProgress;
		this.sumInconclusive = sumInconclusive;
		this.sumNotExecuted = sumNotExecuted;
		this.sumNotRunnable = sumNotRunnable;
		this.sumPassed = sumPassed;
		this.sumPassedButRunAborted = sumPassedButRunAborted;
		this.sumPending = sumPending;
		this.sumTimeOut = sumTimeOut;
		this.sumTotal = sumTotal;
		this.sumWarning = sumWarning;
	}

	/**
	 * Returns the number of aborted tests.
	 * 
	 * @return a {@code String} containing the number of aborted tests.
	 */
	public String getSumAborted() {
		return sumAborted;
	}

	/**
	 * Sets the number of aborted tests to the passed {@code String}.
	 * 
	 * @param sumAborted
	 *            - a {@code String} containing the number of aborted tests to
	 *            be set.
	 */
	public void setSumAborted(String sumAborted) {
		this.sumAborted = sumAborted;
	}

	/**
	 * Returns the number of completed tests.
	 * 
	 * @return a {@code String} containing the number of completed tests.
	 */
	public String getSumCompleted() {
		return sumCompleted;
	}

	/**
	 * Sets the number of completed tests to the passed {@code String}.
	 * 
	 * @param sumCompleted
	 *            - a {@code String} containing the number of completed tests to
	 *            be set.
	 */
	public void setSumCompleted(String sumCompleted) {
		this.sumCompleted = sumCompleted;
	}

	/**
	 * Returns the number of disconnected tests.
	 * 
	 * @return a {@code String} containing the number of disconnected tests.
	 */
	public String getSumDisconnected() {
		return sumDisconnected;
	}

	/**
	 * Sets the number of disconnected tests to the passed {@code String}.
	 * 
	 * @param sumDisconnected
	 *            - a {@code String} containing the number of disconnected tests
	 *            to be set.
	 */
	public void setSumDisconnected(String sumDisconnected) {
		this.sumDisconnected = sumDisconnected;
	}

	/**
	 * Returns the number of tests with errors.
	 * 
	 * @return a {@code String} containing the number of tests with errors.
	 */
	public String getSumError() {
		return sumError;
	}

	/**
	 * Sets the number of tests with errors to the passed {@code String}.
	 * 
	 * @param sumError
	 *            - a {@code String} containing the number of tests with errors
	 *            to be set.
	 */
	public void setSumError(String sumError) {
		this.sumError = sumError;
	}

	// Added sumExecuted everywhere because it's part of the xmls.
	/**
	 * Returns the number of executed tests.
	 * 
	 * @return a {@code String} containing the number of executed tests.
	 */
	public String getSumExecuted() {
		return sumExecuted;
	}

	/**
	 * Sets the number of executed tests to the passed {@code String}.
	 * 
	 * @param sumExecuted
	 *            - a {@code String} containing the number of executed tests to
	 *            be set.
	 */
	public void setSumExecuted(String sumExecuted) {
		this.sumExecuted = sumExecuted;
	}

	/**
	 * Returns the number of failed tests.
	 * 
	 * @return a {@code String} containing the number of failed tests.
	 */
	public String getSumFailed() {
		return sumFailed;
	}

	/**
	 * Sets the number of failed tests to the passed {@code String}.
	 * 
	 * @param sumFailed
	 *            - a {@code String} containing the number of failed tests to be
	 *            set.
	 */
	public void setSumFailed(String sumFailed) {
		this.sumFailed = sumFailed;
	}

	/**
	 * Returns the number of tests in progress.
	 * 
	 * @return a {@code String} containing the number of tests in progress.
	 */
	public String getSumInProgress() {
		return sumInProgress;
	}

	/**
	 * Sets the number of tests in progress to the passed {@code String}.
	 * 
	 * @param sumInProgress
	 *            - a {@code String} containing the number of tests in progress
	 *            to be set.
	 */
	public void setSumInProgress(String sumInProgress) {
		this.sumInProgress = sumInProgress;
	}

	/**
	 * Returns the number of inconclusive tests.
	 * 
	 * @return a {@code String} containing the number of inconclusive tests.
	 */
	public String getSumInconclusive() {
		return sumInconclusive;
	}

	/**
	 * Sets the number of inconclusive tests to the passed {@code String}.
	 * 
	 * @param sumInconclusive
	 *            - a {@code String} containing the number of inconclusive tests
	 *            to be set.
	 */
	public void setSumInconclusive(String sumInconclusive) {
		this.sumInconclusive = sumInconclusive;
	}

	/**
	 * Returns the number of tests that were not executed.
	 * 
	 * @return a {@code String} containing the number of tests that were not
	 *         executed.
	 */
	public String getSumNotExecuted() {
		return sumNotExecuted;
	}

	/**
	 * Sets the number of tests that were not executed to the passed
	 * {@code String}.
	 * 
	 * @param sumNotExecuted
	 *            - a {@code String} containing the number of tests that were
	 *            not executed to be set.
	 */
	public void setSumNotExecuted(String sumNotExecuted) {
		this.sumNotExecuted = sumNotExecuted;
	}

	/**
	 * Returns the number of tests that were not runnable.
	 * 
	 * @return a {@code String} containing the number of tests that were not
	 *         runnable.
	 */
	public String getSumNotRunnable() {
		return sumNotRunnable;
	}

	/**
	 * Sets the number of tests that were not runnable to the passed
	 * {@code String}.
	 * 
	 * @param sumNotRunnable
	 *            - a {@code String} containing the number of tests that were
	 *            not runnable to be set.
	 */
	public void setSumNotRunnable(String sumNotRunnable) {
		this.sumNotRunnable = sumNotRunnable;
	}

	/**
	 * Returns the number of passed tests.
	 * 
	 * @return a {@code String} containing the number of passed tests.
	 */
	public String getSumPassed() {
		return sumPassed;
	}

	/**
	 * Sets the number of passed tests to the passed {@code String}.
	 * 
	 * @param sumPassed
	 *            - a {@code String} containing the number of passed tests to be
	 *            set.
	 */
	public void setSumPassed(String sumPassed) {
		this.sumPassed = sumPassed;
	}

	/**
	 * Returns the number of passed tests that were aborted.
	 * 
	 * @return a {@code String} containing the number of passed tests that were
	 *         aborted.
	 */
	public String getSumPassedButRunAborted() {
		return sumPassedButRunAborted;
	}

	/**
	 * Sets the number of passed tests that were aborted to the passed
	 * {@code String}.
	 * 
	 * @param sumPassedButRunAborted
	 *            - a {@code String} containing the number of passed but aborted
	 *            tests to be set.
	 */
	public void setSumPassedButRunAborted(String sumPassedButRunAborted) {
		this.sumPassedButRunAborted = sumPassedButRunAborted;
	}

	/**
	 * Returns the number of pending tests.
	 * 
	 * @return a {@code String} containing the number of pending tests.
	 */
	public String getSumPending() {
		return sumPending;
	}

	/**
	 * Sets the number of pending tests to the passed {@code String}.
	 * 
	 * @param sumPending
	 *            - a {@code String} containing the number of pending tests to
	 *            be set.
	 */
	public void setSumPending(String sumPending) {
		this.sumPending = sumPending;
	}

	/**
	 * Returns the number of tests that ran out of time.
	 * 
	 * @return a {@code String} containing the number of tests that ran out of
	 *         time.
	 */
	public String getSumTimeOut() {
		return sumTimeOut;
	}

	/**
	 * Sets the number of tests that ran out of time to the passed
	 * {@code String}.
	 * 
	 * @param sumTimeOut
	 *            - a {@code String} containing the number of timed out tests to
	 *            be set.
	 */
	public void setSumTimeOut(String sumTimeOut) {
		this.sumTimeOut = sumTimeOut;
	}

	/**
	 * Returns the total number of tests.
	 * 
	 * @return a {@code String} containing the total number of tests.
	 */
	public String getSumTotal() {
		return sumTotal;
	}

	/**
	 * Sets the total number of tests to the passed {@code String}.
	 * 
	 * @param sumTotal
	 *            - a {@code String} containing the total number of tests to be
	 *            set.
	 */
	public void setSumTotal(String sumTotal) {
		this.sumTotal = sumTotal;
	}

	/**
	 * Returns the number of tests with warnings.
	 * 
	 * @return a {@code String} containing the number of tests with warnings.
	 */
	public String getSumWarning() {
		return sumWarning;
	}

	/**
	 * Sets the number of tests with warnings to the passed {@code String}.
	 * 
	 * @param sumWarning
	 *            - a {@code String} containing the number of tests with
	 *            warnings to be set.
	 */
	public void setSumWarning(String sumWarning) {
		this.sumWarning = sumWarning;
	}

}
