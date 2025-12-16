import PuzzleMetrics from "../models/PuzzleMetrics.jsx";
import PropTypes from "prop-types";
import PuzzleRating from "./PuzzleRating.jsx";
import DifficultyLabel from "./DifficultyLabel.jsx";
import "./PuzzleMetricsBanner.css"

const Metric = ({ label, value }) => (
    <div className="col-6 col-md-3 col-lg">
        <div className="text-muted small mb-1">{label}</div>
        <div className="fw-semibold fs-5">{value}</div>
    </div>
);


const PuzzleMetricsBanner = ({ metrics, difficulty }) => {
    return (
        <div className="card shadow-sm border-0 my-4">
            <div className="card-body py-3">
                <div className="row text-center g-3 align-items-center">

                    <Metric label="Attempted" value={metrics.numAttempted} />
                    <Metric label="Solved" value={metrics.numSolved} />
                    <Metric label="Avg Solve Time" value={`${metrics.avgSolveTime} min`} />
                    <Metric label="Time Worked" value={`${metrics.timeWorkedOn} hrs`} />
                    <Metric label="Avg Hints" value={metrics.avgHintsUsed} />
                    <Metric label="Total Hints" value={metrics.totalHintsUsed} />

                    {difficulty && (
                        <div className="col-6 col-md-3 col-lg">
                            <div className="text-muted small mb-1">Difficulty</div>
                            <DifficultyLabel difficulty={difficulty} />
                        </div>
                    )}

                    <div className="col-12 col-md-4 col-lg">
                        <PuzzleRating
                            rating={metrics.avgRating}
                            numRates={metrics.numRated}
                        />
                    </div>

                </div>
            </div>
        </div>
    );
};


export default PuzzleMetricsBanner;



