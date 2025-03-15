import Link from "next/link";
import BookGrid from "@/components/ag-grid";

export default async function Books() {
  const data = await fetch("http://localhost:8080/books");
  const books = await data.json();

  return (
    <div className="flex flex-col min-h-screen p-8 sm:p-12">
      <div className="mb-8">
        <h1 className="text-4xl font-bold mb-4">Books</h1>
        <div className="flex items-center gap-4">
          <Link
            href="/books/new"
            className="px-4 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
          >
            Add Book
          </Link>
        </div>
      </div>
      <div className="flex-grow h-[calc(100vh-12rem)] rounded-lg border">
        <BookGrid books={books} />
      </div>
    </div>
  );
}
