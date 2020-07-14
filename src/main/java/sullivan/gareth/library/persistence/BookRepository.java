package sullivan.gareth.library.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;
import sullivan.gareth.library.model.Book;

public interface BookRepository extends PagingAndSortingRepository <Book, Long> {
}
