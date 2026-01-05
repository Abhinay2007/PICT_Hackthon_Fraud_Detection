import css from "../styles/PieLegend.module.css";

const PieLegend = ({ data }) => {
  return (
    <div className={css.legend}>
      {data.map((item, index) => (
        <div key={index} className={css.item}>
          <span
            className={css.colorDot}
            style={{ background: item.fill }}
          />
          <span className={css.label}>{item.name}</span>
          <span className={css.value}>{item.value}</span>
        </div>
      ))}
    </div>
  );
};

export default PieLegend;
