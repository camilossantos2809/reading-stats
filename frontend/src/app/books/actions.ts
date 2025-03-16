"use server";

import { Book } from "@/types";
import { redirect } from "next/navigation";

export async function addBook(_: unknown, formData: FormData) {
  const book = {
    isbn: formData.get("isbn"),
    name: formData.get("name"),
    author: formData.get("author") || null,
    pages: formData.get("pages") ? Number(formData.get("pages")) : null,
  };

  const response = await fetch("http://localhost:8080/books", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(book),
  });

  if (response.ok) {
    redirect("/books");
  }

  console.error(response);

  let message = "";
  try {
    const data = await response.json();
    message = data.message;
  } catch (e) {
    console.error(`Failed to parse error message: ${e}`);
  }

  return {
    message: `Failed to add book: ${response.status} ${response.statusText}. ${message}`,
  };
}

export async function editBook(book: Book) {
  const response = await fetch(`http://localhost:8080/books/${book.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      isbn: book.isbn,
      name: book.name,
      author: book.author,
      pages: book.pages,
    }),
  });

  if (response.ok) {
    redirect("/books");
  }

  console.error(response);

  let message = "";
  try {
    const data = await response.json();
    message = data.message;
  } catch (e) {
    console.error(`Failed to parse error message: ${e}`);
  }

  return {
    message: `Failed to update book: ${response.status} ${response.statusText}. ${message}`,
  };
}
