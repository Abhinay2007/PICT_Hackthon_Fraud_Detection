import { FileText } from "lucide-react";

export default function SampleButton() {
  const handleDownload = () => {
    const link = document.createElement("a");
    link.href = "/sample_transactions.csv";   // from public folder
    link.download = "sample_transactions.csv";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (
    <button
      className="max-w-4xl m-auto
        w-full py-4 px-6
        bg-slate-900/50 border border-slate-800
        rounded-2xl text-slate-300 font-medium
        hover:bg-slate-800 transition
        flex items-center justify-center gap-3"
      onClick={handleDownload}
    >
      <FileText size={18} />
      Generate Sample Transaction Data
    </button>
  );
}