import Header from "./Header";
import UploadZone from "./UploadZone";
import SampleButton from "./SampleButton";
import Footer from "./Footer";

const UploadPage = () => {
    return (
        <div className="min-h-screen bg-[#020617] text-slate-200 font-sans">
      <Header />

      <main className=" mx-auto px-6 pt-16 pb-24 text-center">
        {/* HERO */}
        <h1 className="text-5xl md:text-6xl font-bold text-white mb-6">
          Spot the{" "}
          <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-blue-600">
            Anomaly
          </span>
        </h1>

        <p className="text-slate-400 text-lg max-w-2xl mx-auto mb-12">
          Upload your transaction data and let our AI-powered system detect
          suspicious patterns in seconds.
        </p>

        {/* UPLOAD */}
        <UploadZone />

        {/* DIVIDER */}
        <div className="max-w-4xl relative my-12 m-auto">
          <div className="absolute inset-0 flex items-center">
            <div className="w-full border-t border-slate-800"></div>
          </div>
          <div className="relative flex justify-center uppercase">
            <span className="bg-[#020617] px-4 text-xs text-slate-600 tracking-widest">
              or
            </span>
          </div>
        </div>


        {/* BUTTON */}
        <SampleButton />

        {/* FOOTER */}
        <Footer />
      </main>
    </div>
    )
}

export default UploadPage;