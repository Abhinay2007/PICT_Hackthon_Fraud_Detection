import css from "../styles/LocationFraudTooltip.module.css";

const LocationFraudTooltip = ({ active, payload, label }) => {
  if (!active || !payload || !payload.length) return null;

  const count = payload[0].value;

  return (
    <div className={css.tooltip}>
      <p className={css.location}>{label}</p>

      <div className={css.row}>
        <span className={css.dot} />
        <span className={css.text}>Fraud Transactions</span>
        <span className={css.value}>{count}</span>
      </div>
    </div>
  );
};

export default LocationFraudTooltip;
