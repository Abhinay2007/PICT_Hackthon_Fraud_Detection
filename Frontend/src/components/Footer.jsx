export default function Footer() {
  return (
    <footer className="mt-12 pb-10 border-t border-slate-900/50 pt-8">
      <div className="text-center text-slate-500 text-sm tracking-widest uppercase font-semibold">
        <p>© {new Date().getFullYear()} CERBERUS AI. All rights reserved.</p>
      </div>
    </footer>
  );
}
