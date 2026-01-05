import css from "../styles/DashboardLoader.module.css";

const DashboardLoader = ({ message = "Analyzing Transactions" }) => {
  return (
    <div className={css.loaderWrapper}>
      <div className={css.core}>
        <div className={css.ring}></div>
        <div className={css.ring}></div>
        <div className={css.ring}></div>
      </div>

      <p className={css.text}>
        {message}
        <span className={css.dots}>
          <span>.</span>
          <span>.</span>
          <span>.</span>
        </span>
      </p>

      <p className={css.subtext}>
        Initializing anomaly engine & pattern analysis
      </p>
    </div>
  );
};

export default DashboardLoader;
