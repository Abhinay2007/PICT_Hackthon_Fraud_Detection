import { ShieldCheck, Zap } from "lucide-react";

export default function Header() {
  return (
    <nav className="flex justify-between items-center px-8 py-6 max-w-7xl mx-auto">
      <div className="flex items-center gap-3">
        <div className="bg-blue-600/20 p-2 rounded-lg border border-blue-500/30">
          <ShieldCheck className="text-blue-500 w-6 h-6" />
        </div>
        <div>
          <div className="flex items-center gap-2">
            <span className="font-bold text-xl text-white">CERBERUS</span>
            <span className="bg-blue-600/20 text-blue-400 text-[10px] px-1.5 py-0.5 rounded border font-bold">
              AI
            </span>
          </div>
          <p className="text-xs text-slate-500">Transaction Anomaly Detector</p>
        </div>
      </div>

      <div className="flex items-center gap-2 text-orange-500 bg-orange-500/10 px-3 py-1.5 rounded-full border text-sm">
        <Zap size={14} fill="currentColor" />
        Real-time Analysis
      </div>
    </nav>
  );
}
