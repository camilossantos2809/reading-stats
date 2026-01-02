"use server";

import { redirect } from "next/navigation";

import { Book } from "@/types";
import { FormStatusProps } from "@/components/form/FormStatus";

export async function addBook(
  _: unknown,
  formData: FormData
): Promise<FormStatusProps> {
  const book = {
    isbn: formData.get("isbn"),
    name: formData.get("name"),
    author: formData.get("author") || null,
    pages: formData.get("pages") ? Number(formData.get("pages")) : null,
  };

  let response: Response;
  try {
    response = await fetch("http://localhost:8080/books", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(book),
    });
  } catch (error) {
    return {
      message: `Failed to add book: ${error}`,
      status: "error",
    };
  }

  if (response.ok) {
    redirect("/books");
  }

  let message = "";
  try {
    const data = await response.json();
    message = data.message;
  } catch (error) {
    console.error(`Failed to parse error message: ${error}`);
  }

  return {
    message: `Failed to add book: ${response.status} ${response.statusText}. ${message}`,
    status: "error",
  };
}

export async function editBook(book: Book) {
  const response = await fetch(`http://localhost:8080/books/${book.id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
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
