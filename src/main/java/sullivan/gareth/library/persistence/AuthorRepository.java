package sullivan.gareth.library.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;
import sullivan.gareth.library.model.Author;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
}
