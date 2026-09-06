/**
 * Client-side countdown, purely for UX (showing the student time left
 * and auto-submitting their form at zero). This is NOT the source of
 * truth for grading — SubmitQuizServlet independently checks the
 * server-side start time stored in QuizSession, so a student can't
 * fake extra time by editing this script or pausing JS execution.
 */
function startTimer(secondsRemaining, displayElementId, formId) {

    var display = document.getElementById(displayElementId);
    var form = document.getElementById(formId);
    var submitted = false;

    function tick() {

        var minutes = Math.floor(secondsRemaining / 60);
        var seconds = secondsRemaining % 60;

        display.textContent = minutes + ":" + (seconds < 10 ? "0" : "") + seconds;

        if (secondsRemaining <= 0) {
            clearInterval(intervalId);
            if (!submitted) {
                submitted = true;
                form.submit();
            }
            return;
        }

        secondsRemaining--;
    }

    tick();
    var intervalId = setInterval(tick, 1000);
}
