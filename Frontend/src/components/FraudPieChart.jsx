import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  ResponsiveContainer
} from "recharts";
import PieLegend from "./PieLegend";

const COLORS = [
  "url(#redGradient)",
  "url(#amberGradient)",
  "url(#blueGradient)",
  "url(#greenGradient)"
];

export default function FraudPieChart({ fraudTransationDistribution }) {
  const data = Object.entries(fraudTransationDistribution).map(([name, value]) => ({
    name,
    value
  }));

  return (
    <div>
    <ResponsiveContainer width="100%" height={280}>
      <PieChart>
        {/*  Gradients */}
        <defs>
          <linearGradient id="redGradient" x1="0" y1="0" x2="1" y2="1">
            <stop offset="0%" stopColor="#af0909ff" />
            <stop offset="100%" stopColor="#7f1d1d" />
          </linearGradient>
          <linearGradient id="amberGradient" x1="0" y1="0" x2="1" y2="1">
            <stop offset="0%" stopColor="#f59e0b" />
            <stop offset="100%" stopColor="#af4406ff" />
          </linearGradient>
          <linearGradient id="blueGradient" x1="0" y1="0" x2="1" y2="1">
            <stop offset="0%" stopColor="#0a65f7ff" />
            <stop offset="100%" stopColor="#0c39b4ff" />
          </linearGradient>
          <linearGradient id="greenGradient" x1="0" y1="0" x2="1" y2="1">
            <stop offset="0%" stopColor="#0aeca1ff" />
            <stop offset="100%" stopColor="#09b587ff" />
          </linearGradient>
        </defs>

        <Pie
          data={data}
          dataKey="value"
          cx="50%"
          cy="50%"
          innerRadius={70}
          outerRadius={110}
          paddingAngle={4}
          isAnimationActive={true}
          animationDuration={1200}
          label={({ percent }) =>
            `${(percent * 100).toFixed(0)}%`
          }
        >
          {data.map((_, index) => (
            <Cell key={index} fill={COLORS[index % COLORS.length]} />
          ))}
        </Pie>

        <Tooltip
          formatter={(value, name) => [`${value}`, name]}
          contentStyle={{
            backgroundColor: "#b4b6bdff",
            border: "1px solid rgba(148,163,184,0.2)",
            borderRadius: "10px",
            color: "#e5e7eb"
          }}
        />
      </PieChart>
    </ResponsiveContainer>
      {/*  CUSTOM LEGEND */}
      <PieLegend data={data} />
    </div>
  );
}
