import css from "../styles/KPICards.module.css"

const KPICards = ({totalTransactions, activeAnomalies, avgTransactionAmount, topRiskRegion, anomalyPercentageAvg}) => {
    return <div className={`${css["card-container"]}`}>
        <div className={css.card}>
            <h6 className={css.cardHeading}>Processed Logs</h6>
            <h1 className={css.cardDetail}>{totalTransactions}</h1>
        </div>
        <div className={css.card}>
            <h6 className={css.cardHeading}>Active Anomalies</h6>
            <h1 className={css.cardDetail}>{activeAnomalies}</h1>
        </div>
        <div className={css.card}>
            <h6 className={css.cardHeading}>Average Transaction Amount</h6>
            <h1 className={css.cardDetail}>₹{avgTransactionAmount}</h1>
        </div>
        <div className={css.card}>
            <h6 className={css.cardHeading}>Top Risk Region</h6>
            <h1 className={css.cardDetail}>{topRiskRegion}</h1>
        </div>
        <div className={css.card}>
            <h6 className={css.cardHeading}>Average Anomaly Percentage</h6>
            <h1 className={css.cardDetail}>{anomalyPercentageAvg}%</h1>
        </div>
    </div>
}

export default KPICards;