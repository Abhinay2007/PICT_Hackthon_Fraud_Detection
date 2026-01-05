import css from "../styles/FraudTransactionDetails.module.css";

const FraudTransactionDetails = ({ flagedTransactionDetails }) => {
    return (
        <div className={css.tableWrapper}>
            <table className={css.table}>
                <thead>
                    <tr>
                        <th>Transaction ID</th>
                        <th>User ID</th>
                        <th>Amount</th>
                        <th>Location</th>
                        <th>Risk Assessment</th>
                    </tr>
                </thead>

                <tbody>
                    {flagedTransactionDetails.map((row, index) => {
                        const isSafe = !row.isFraud;

                        return (
                            <tr key={index} className={isSafe ? css.safe : css.risky}>
                                <td className={css.mono}>{row.transactionId}</td>
                                <td className={css.mono}>{row.userId}</td>
                                <td>₹ {row.amount?.toLocaleString()}</td>
                                <td>{row.location}</td>
                                <td>
                                    <div className={css.riskCell}>
                                        <span className={isSafe ? css.safeBadge : css.riskBadge}>
                                            {isSafe ? "Normal" : "Anomaly"}
                                        </span>

                                        {!isSafe && (
                                            <p className={css.explanation}>
                                                {row.explaination}
                                            </p>
                                        )}
                                    </div>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
};

export default FraudTransactionDetails;
