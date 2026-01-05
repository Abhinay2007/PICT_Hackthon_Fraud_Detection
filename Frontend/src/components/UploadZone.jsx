import { useEffect, useState } from "react";
import { Upload } from "lucide-react";
import { sendFile } from "../store/store";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

export default function UploadZone() {
    const [isDragging, setIsDragging] = useState(false);
    const [file, setFile] = useState(null);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        if (file) {
            dispatch(sendFile(file, setFile));
            navigate("/dashboard")
        }
        }, [file]);

    return (
        <div
            onClick={() => document.getElementById("fileUpload").click()}
            onDragOver={(e) => {
                e.preventDefault();
                setIsDragging(true);
            }}
            onDragLeave={() => setIsDragging(false)}
            onDrop={(e) => {
                e.preventDefault();
                setIsDragging(false);
                setFile(e.dataTransfer.files[0]);
            }}
            className={`max-w-4xl m-auto
        relative group cursor-pointer
        border-2 border-dashed rounded-3xl p-12
        transition-all duration-300 ease-out
        ${isDragging
                    ? "border-blue-500 bg-blue-500/5 shadow-[0_0_40px_rgba(59,130,246,0.25)]"
                    : "border-slate-800 bg-slate-900/30 hover:border-blue-500/60 hover:bg-blue-500/5"
                }
      `}
        >
            {/* Glow ring */}
            <div className="absolute inset-0 rounded-3xl pointer-events-none group-hover:shadow-[0_0_60px_rgba(59,130,246,0.15)] transition-all duration-300" />

            <div className="relative flex flex-col items-center gap-4">
                {/* Icon */}
                <div
                    className="
            bg-slate-800/50 p-4 rounded-2xl
            transition-all duration-300
            group-hover:scale-110
            group-hover:bg-blue-500/10
            group-hover:shadow-[0_0_30px_rgba(59,130,246,0.35)]
          "
                >
                    <Upload className="text-slate-400 w-10 h-10 group-hover:text-blue-400 transition-colors duration-300" />
                </div>

                <h3 className="text-xl font-semibold text-white">
                    Drop your transaction file here
                </h3>

                <p className="text-slate-500">or click to browse</p>

                <div className="flex gap-2 mt-2">
                    {[".csv", ".xls", ".xlsx"].map((ext) => (
                        <span
                            key={ext}
                            className="bg-slate-800 px-3 py-1 rounded-md text-xs font-mono text-slate-400 border border-slate-700">
                            {ext}
                        </span>
                    ))}
                </div>

                <input
                    type="file"
                    accept=".csv,.xls,.xlsx"
                    className="hidden"
                    id="fileUpload"
                    onChange={(e) => setFile(e.target.files[0])}
                />
            </div>
        </div>
    );
}
