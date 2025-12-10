package TD_API_Orale.JT.API_JTBM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorStatsDTO {
    private Long authorId;
    private String firstName;
    private String lastName;
    private Long bookCount;
}
