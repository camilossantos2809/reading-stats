import Link from "next/link";

export default function Home() {
  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-balthazar-sans)]">
      <main className="flex flex-col gap-8 row-start-2 items-center sm:items-start">
        <div className="flex flex-col gap-4">
          <h1 className="text-4xl font-bold">Reading Stats</h1>
          <p className="text-lg">Keep track of your reading stats and goals</p>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-6 mb-12">
          <Link
            href="/books"
            className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600 group"
          >
            <h2 className="text-2xl font-bold mb-2">Books</h2>
            <p className="text-gray-400 group-hover:text-foreground transition-colors duration-300">
              Browse and manage your book collection
            </p>
          </Link>
          <Link
            href="/goals"
            className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600 group"
          >
            <h2 className="text-2xl font-bold mb-2">Goals</h2>
            <p className="text-gray-400 group-hover:text-foreground transition-colors duration-300">
              Set and track your reading goals
            </p>
          </Link>
          <Link
            href="/history"
            className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600 group"
          >
            <h2 className="text-2xl font-bold mb-2">History</h2>
            <p className="text-gray-400 group-hover:text-foreground transition-colors duration-300">
              View your reading history and progress
            </p>
          </Link>
        </div>
        <div className="w-full">
          <h2 className="text-3xl font-bold mb-8">Stats</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            <div className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600">
              <p className="text-gray-400">Books Read</p>
              <p className="text-4xl font-bold">0</p>
            </div>
            <div className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600">
              <p className="text-gray-400">Pages Read</p>
              <p className="text-4xl font-bold">0</p>
            </div>
            <div className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600">
              <p className="text-gray-400">Reading Time</p>
              <p className="text-4xl font-bold">0h</p>
            </div>
            <div className="p-6 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600">
              <p className="text-gray-400">Current Streak</p>
              <p className="text-4xl font-bold">0</p>
            </div>
          </div>
        </div>
      </main>
      <footer className="row-start-3 flex gap-6 flex-wrap items-center justify-center"></footer>
    </div>
  );
}
