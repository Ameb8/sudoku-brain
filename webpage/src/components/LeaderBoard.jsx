import { useState, useEffect } from "react";
import { Container, Table, Alert, Spinner, Row, Col } from "react-bootstrap";
import PageSizeSelector from "./PageSizeSelector";
import PageSelector from "./PageSelector";
import Leader from "../models/Leader";

const Leaderboard = () => {
    const [leaders, setLeaders] = useState([]);
    const [pageSize, setPageSize] = useState(10);
    const [currentPage, setCurrentPage] = useState(1);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchLeaders = async (page, size) => {
        setIsLoading(true);
        setError(null);
        try {
            const response = await fetch(`http://localhost:8080/api/users/leaderboard/${page - 1}/${size}`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const data = await response.json();
            const fetchedLeaders = data.map(
                (item) => new Leader(item.username, item.puzzlesSolved, item.avgSolveTime, item.avgHintsUsed)
            );
            setLeaders(fetchedLeaders);
        } catch (err) {
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchLeaders(currentPage, pageSize);
    }, [currentPage, pageSize]);

    const handlePageSizeChange = (size) => {
        setPageSize(size);
        setCurrentPage(1);
    };

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    return (
        <Container className="py-4">
            <h2 className="text-center mb-4">Leaderboard</h2>

            {isLoading && <Spinner animation="border" className="d-block mx-auto" />}

            {error && <Alert variant="danger" className="text-center">{error}</Alert>}

            {!isLoading && !error && (
                <>
                    <Table striped bordered hover responsive className="bg-white">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Username</th>
                            <th>Puzzles Solved</th>
                            <th>Avg Solve Time</th>
                            <th>Avg Hints Used</th>
                        </tr>
                        </thead>
                        <tbody>
                        {leaders.length === 0 ? (
                            <tr>
                                <td colSpan="5" className="text-center">No data available</td>
                            </tr>
                        ) : (
                            leaders.map((leader, index) => (
                                <tr key={index}>
                                    <td>{(currentPage - 1) * pageSize + index + 1}</td>
                                    <td>{leader.username}</td>
                                    <td>{leader.puzzlesSolved}</td>
                                    <td>{leader.avgSolveTime}</td>
                                    <td>{leader.avgHintsUsed}</td>
                                </tr>
                            ))
                        )}
                        </tbody>
                    </Table>

                    <Row className="mt-3">
                        <Col md={6}>
                            <PageSizeSelector onChange={handlePageSizeChange} />
                        </Col>
                        <Col md={6} className="text-end">
                            <PageSelector pageSize={pageSize} onPageChange={handlePageChange} />
                        </Col>
                    </Row>
                </>
            )}
        </Container>
    );
};

export default Leaderboard;




/*
import { useState, useEffect } from "react";
import PageSizeSelector from "./PageSizeSelector";
import PageSelector from "./PageSelector";
import ShowLeader from "./ShowLeader";
import Leader from "../models/Leader";

const Leaderboard = () => {
    const [leaders, setLeaders] = useState([]); // List of Leader objects
    const [pageSize, setPageSize] = useState(10); // Results per page
    const [currentPage, setCurrentPage] = useState(1); // Current page
    const [isLoading, setIsLoading] = useState(false); // Loading state
    const [error, setError] = useState(null); // Error state

    // Fetch leaderboard data
    const fetchLeaders = async (page, size) => {

        setIsLoading(true);
        setError(null); // Reset error state
        try {
            const response = await fetch(`http://localhost:8080/api/users/leaderboard/${page - 1}/${size}`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const data = await response.json();

            const fetchedLeaders = data.map(
                (item) => new Leader(item.username, item.puzzlesSolved, item.avgSolveTime, item.avgHintsUsed)
            );
            setLeaders(fetchedLeaders);
        } catch (err) {
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    };

    // Fetch data when page or pageSize changes
    useEffect(() => {
        fetchLeaders(currentPage, pageSize);
    }, [currentPage, pageSize]);

    // Handle page size change
    const handlePageSizeChange = (size) => {
        setPageSize(size);
        setCurrentPage(1); // Reset to page 1 when page size changes
    };

    // Handle page change
    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    return (
        <div style={{ width: "95%", margin: "auto", textAlign: "center" }}>
            {isLoading && <p>Loading...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
            <div
                style={{
                    border: "3px solid black",
                    borderRadius: "8px",
                    padding: "1rem",
                    height: "4%",
                    overflowY: "scroll",
                    background : "lightyellow"
                }}
            >
                {leaders.length === 0 && !isLoading && <p>No data available</p>}
                {leaders.map((leader, index) => (
                    <ShowLeader key={index} leader={leader} index = {index + 1}/>
                ))}
            </div>
            <PageSizeSelector onChange={handlePageSizeChange} />
            <PageSelector pageSize={pageSize} onPageChange={handlePageChange} />
        </div>
    );
};

export default Leaderboard;

 */