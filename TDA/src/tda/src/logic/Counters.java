package tda.src.logic;

public class Counters {
	String sumAborted;
	String sumCompleted;
	String sumDisconnected;
	String sumError;
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

	public Counters(String sumAborted, String sumCompleted, String sumDisconnected, String sumError, String sumFailed,
			String sumInProgress, String sumInconclusive, String sumNotExecuted, String sumNotRunnable,
			String sumPassed, String sumPassedButRunAborted, String sumPending, String sumTimeOut, String sumTotal,
			String sumWarning) {
		super();
		this.sumAborted = sumAborted;
		this.sumCompleted = sumCompleted;
		this.sumDisconnected = sumDisconnected;
		this.sumError = sumError;
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

	public String getSumAborted() {
		return sumAborted;
	}

	public void setSumAborted(String sumAborted) {
		this.sumAborted = sumAborted;
	}

	public String getSumCompleted() {
		return sumCompleted;
	}

	public void setSumCompleted(String sumCompleted) {
		this.sumCompleted = sumCompleted;
	}

	public String getSumDisconnected() {
		return sumDisconnected;
	}

	public void setSumDisconnected(String sumDisconnected) {
		this.sumDisconnected = sumDisconnected;
	}

	public String getSumError() {
		return sumError;
	}

	public void setSumError(String sumError) {
		this.sumError = sumError;
	}

	public String getSumFailed() {
		return sumFailed;
	}

	public void setSumFailed(String sumFailed) {
		this.sumFailed = sumFailed;
	}

	public String getSumInProgress() {
		return sumInProgress;
	}

	public void setSumInProgress(String sumInProgress) {
		this.sumInProgress = sumInProgress;
	}

	public String getSumInconclusive() {
		return sumInconclusive;
	}

	public void setSumInconclusive(String sumInconclusive) {
		this.sumInconclusive = sumInconclusive;
	}

	public String getSumNotExecuted() {
		return sumNotExecuted;
	}

	public void setSumNotExecuted(String sumNotExecuted) {
		this.sumNotExecuted = sumNotExecuted;
	}

	public String getSumNotRunnable() {
		return sumNotRunnable;
	}

	public void setSumNotRunnable(String sumNotRunnable) {
		this.sumNotRunnable = sumNotRunnable;
	}

	public String getSumPassed() {
		return sumPassed;
	}

	public void setSumPassed(String sumPassed) {
		this.sumPassed = sumPassed;
	}

	public String getSumPassedButRunAborted() {
		return sumPassedButRunAborted;
	}

	public void setSumPassedButRunAborted(String sumPassedButRunAborted) {
		this.sumPassedButRunAborted = sumPassedButRunAborted;
	}

	public String getSumPending() {
		return sumPending;
	}

	public void setSumPending(String sumPending) {
		this.sumPending = sumPending;
	}

	public String getSumTimeOut() {
		return sumTimeOut;
	}

	public void setSumTimeOut(String sumTimeOut) {
		this.sumTimeOut = sumTimeOut;
	}

	public String getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(String sumTotal) {
		this.sumTotal = sumTotal;
	}

	public String getSumWarning() {
		return sumWarning;
	}

	public void setSumWarning(String sumWarning) {
		this.sumWarning = sumWarning;
	}

}
